package io.dodn.commerce.core.domain.settlement;

import io.dodn.commerce.core.enums.PaymentState;
import io.dodn.commerce.core.enums.TransactionType;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import io.dodn.commerce.storage.db.core.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {

    private final PaymentRepository paymentRepository;
    private final SettlementTargetLoader settlementTargetLoader;

    public void loadTargets(LocalDate settleDate, LocalDateTime from, LocalDateTime to) {
        Pageable paymentPageable = PageRequest.of(0, 1000, Sort.by(Sort.Direction.ASC, "id"));
        Slice<PaymentEntity> payments;
        do {
            payments = paymentRepository.findAllByStateAndPaidAtBetween(PaymentState.SUCCESS, from, to, paymentPageable);
            try {
                Map<Long, Long> paymentMap = payments.getContent()
                        .stream()
                        .collect(Collectors.toMap(PaymentEntity::getOrderId, PaymentEntity::getId));

                settlementTargetLoader.process(settleDate, TransactionType.PAYMENT, paymentMap);
            } catch (Exception e) {
                log.error("[SETTLEMENT_LOAD_TARGETS] `결제` 거래건 정산 대상 생성 중 오류 발생 offset: {} size: {} page: {} error: {}", paymentPageable.getOffset(), paymentPageable.getPageSize(), paymentPageable.getPageNumber(), e.getMessage(), e);
            }
            paymentPageable = payments.nextPageable();
        } while (payments.hasNext());

        // todo cancel에 대한 정산 process
    }
}
