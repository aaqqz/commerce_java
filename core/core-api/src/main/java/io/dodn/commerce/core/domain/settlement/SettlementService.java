package io.dodn.commerce.core.domain.settlement;

import io.dodn.commerce.core.enums.PaymentState;
import io.dodn.commerce.core.enums.SettlementState;
import io.dodn.commerce.core.enums.TransactionType;
import io.dodn.commerce.storage.db.core.payment.CancelEntity;
import io.dodn.commerce.storage.db.core.payment.CancelRepository;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import io.dodn.commerce.storage.db.core.payment.PaymentRepository;
import io.dodn.commerce.storage.db.core.settlement.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {

    private final PaymentRepository paymentRepository;
    private final CancelRepository cancelRepository;
    private final SettlementTargetLoader settlementTargetLoader;
    private final SettlementTargetRepository settlementTargetRepository;
    private final SettlementRepository settlementRepository;

    public void loadTargets(LocalDate settleDate, LocalDateTime from, LocalDateTime to) {
        Pageable paymentPageable = PageRequest.of(0, 1000, Sort.by(Sort.Direction.ASC, "id"));
        Slice<PaymentEntity> payments;
        do {
            payments = paymentRepository.findAllByStateAndPaidAtBetween(PaymentState.SUCCESS, from, to, paymentPageable);
            try {
                Map<Long, Long> paymentMap = payments.getContent().stream()
                        .collect(Collectors.toMap(
                                PaymentEntity::getOrderId,
                                PaymentEntity::getId
                        ));

                settlementTargetLoader.process(settleDate, TransactionType.PAYMENT, paymentMap);
            } catch (Exception e) {
                log.error("[SETTLEMENT_LOAD_TARGETS] `결제` 거래건 정산 대상 생성 중 오류 발생 offset: {} size: {} page: {} error: {}", paymentPageable.getOffset(), paymentPageable.getPageSize(), paymentPageable.getPageNumber(), e.getMessage(), e);
            }
            paymentPageable = payments.nextPageable();
        } while (payments.hasNext());


        Pageable cancelPageable = PageRequest.of(0, 1000, Sort.by(Sort.Direction.ASC, "id"));
        Slice<CancelEntity> cancels;
        do {
            cancels = cancelRepository.findAllByCanceledAtBetween(from, to, cancelPageable);
            try {
                Map<Long, Long> cancleMap = cancels.getContent().stream()
                        .collect(Collectors.toMap(
                                CancelEntity::getOrderId,
                                CancelEntity::getId
                        ));

                settlementTargetLoader.process(settleDate, TransactionType.CANCEL, cancleMap);
            } catch (Exception e) {
                log.error("[SETTLEMENT_LOAD_TARGETS] `취소` 거래건 정산 대상 생성 중 오류 발생 offset: {} size: {} page: {} error: {}", cancelPageable.getOffset(), cancelPageable.getPageSize(), cancelPageable.getPageNumber(), e.getMessage(), e);
            }
            cancelPageable = cancels.nextPageable();
        } while (cancels.hasNext());
    }

    @Transactional
    public Integer calculate(LocalDate settleDate) {
        List<SettlementTargetSummary> summary = settlementTargetRepository.findBySummary(settleDate);
        List<SettlementEntity> settlements = summary.stream()
                .map(it -> {
                    SettlementAmount amount = SettlementCalculator.calculate(it.targetAmount());
                    return SettlementEntity.create(
                            it.merchantId(),
                            it.settlementDate(),
                            amount.originalAmount(),
                            amount.feeAmount(),
                            amount.feeRate(),
                            amount.settlementAmount()
                    );
                })
                .toList();

        settlementRepository.saveAll(settlements);
        return settlements.size();
    }


    public Integer transfer() {
        Map<Long, List<SettlementEntity>> settlements = settlementRepository.findByState(SettlementState.READY)
                .stream()
                .collect(Collectors.groupingBy(SettlementEntity::getMerchantId));

        for (Map.Entry<Long, List<SettlementEntity>> settlement : settlements.entrySet()) {
            try {
                BigDecimal transferAmount = settlement.getValue().stream()
                        .map(SettlementEntity::getSettlementAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    // NOTE: 총 정산금이 음수라면 돈 보낼 필요가 없다는 것이므로, 정산금이 양수가 될 때까지 스킵
                    log.warn("[SETTLEMENT_TRANSFER] {} 가맹점 미정산 금액 : {} 발생 확인 요망!", settlement.getKey(), transferAmount);
                    continue;
                }

                /**
                 * NOTE: 외부 펌 등 이체 서비스 API 호출
                 */

                settlement.getValue().forEach(SettlementEntity::sent);
                settlementRepository.saveAll(settlement.getValue());
            } catch (Exception e) {
                log.error("[SETTLEMENT_TRANSFER] {} 가맹점 정산 중 에러 발생: {}", settlement.getKey(), e.getMessage(), e);
            }
        }
        return settlements.size();
    }
}
