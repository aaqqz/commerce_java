package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.order.TransactionHistoryEntity;
import io.dodn.commerce.storage.db.core.order.TransactionHistoryRepository;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionHistoryManager {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public void createSuccess(OrderEntity order, PaymentEntity payment, String externalPaymentKey) {
        TransactionHistoryEntity transactionHistoryEntity = TransactionHistoryEntity.createSuccess(
                order.getUserId(),
                order.getId(),
                payment.getId(),
                externalPaymentKey,
                payment.getPaidAmount(),
                "결제 성공",
                payment.getPaidAt()
        );

        transactionHistoryRepository.save(transactionHistoryEntity);
    }

    public void createFail(OrderEntity order, PaymentEntity payment, String code, String message) {
        TransactionHistoryEntity transactionHistoryEntity = TransactionHistoryEntity.createFail(
                order.getUserId(),
                order.getId(),
                payment.getId(),
                payment.getPaidAmount(),
                code,
                message
        );

        transactionHistoryRepository.save(transactionHistoryEntity);
    }
}
