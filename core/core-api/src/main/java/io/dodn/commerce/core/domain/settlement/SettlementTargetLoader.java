package io.dodn.commerce.core.domain.settlement;

import io.dodn.commerce.core.enums.TransactionType;
import io.dodn.commerce.storage.db.core.order.OrderItemEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemRepository;
import io.dodn.commerce.storage.db.core.settlement.MerchantProductMappingEntity;
import io.dodn.commerce.storage.db.core.settlement.MerchantProductMappingRepository;
import io.dodn.commerce.storage.db.core.settlement.SettlementTargetEntity;
import io.dodn.commerce.storage.db.core.settlement.SettlementTargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SettlementTargetLoader {

    private final OrderItemRepository orderItemRepository;
    private final MerchantProductMappingRepository merchantProductMappingRepository;
    private final SettlementTargetRepository settlementTargetRepository;

    @Transactional
    public void process(LocalDate settleDate, TransactionType transactionType, Map<Long, Long> transactionIdMap) {
        List<OrderItemEntity> orderItems = orderItemRepository.findByOrderIdIn(transactionIdMap.keySet());
        Set<Long> productIds = orderItems.stream()
                .map(OrderItemEntity::getProductId)
                .collect(Collectors.toSet());

        var merchantMappingMap = merchantProductMappingRepository.findByProductIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(
                        MerchantProductMappingEntity::getProductId,
                        merchantProductMapping -> merchantProductMapping
                ));

        List<SettlementTargetEntity> targets = orderItems.stream()
                .map(item -> {
                    MerchantProductMappingEntity mapping = merchantMappingMap.get(item.getProductId());
                    if (mapping == null || mapping.getMerchantId() == null) {
                        throw new IllegalStateException("상품 " + item.getProductId() + " 의 가맹점 매핑이 존재하지 않음");
                    }
                    Long merchantId = mapping.getMerchantId();

                    Long transactionId = transactionIdMap.get(item.getOrderId());
                    if (transactionId == null) {
                        throw new IllegalStateException("주문 " + item.getOrderId() + " 의 거래 ID 매핑이 존재하지 않음");
                    }

                    // targetAmount
                    BigDecimal targetAmount = switch (transactionType) {
                        case PAYMENT -> item.getTotalPrice();
                        case CANCEL -> item.getTotalPrice().negate();
                        default -> throw new UnsupportedOperationException();
                    };

                    return SettlementTargetEntity.create(
                            merchantId,
                            settleDate,
                            targetAmount,
                            transactionType,
                            transactionId,
                            item.getOrderId(),
                            item.getProductId(),
                            item.getQuantity(),
                            item.getUnitPrice(),
                            item.getTotalPrice()
                    );
                })
                .toList();

        settlementTargetRepository.saveAll(targets);
    }
}
