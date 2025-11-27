package io.dodn.commerce.storage.db.core.order;

import io.dodn.commerce.core.enums.TransactionType;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "transaction_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHistoryEntity extends BaseEntity {

    private Long userId;
    private Long orderId;
    private Long paymentId;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private TransactionType type;

    private String externalPaymentKey;
    private BigDecimal amount;
    private String message;
    private LocalDateTime occurredAt;

    public static TransactionHistoryEntity createSuccess(Long userId, Long orderId, Long paymentId, String externalPaymentKey, BigDecimal amount, String message, LocalDateTime occurredAt) {
        TransactionHistoryEntity entity = new TransactionHistoryEntity();
        entity.userId = userId;
        entity.orderId = orderId;
        entity.paymentId = paymentId;
        entity.type = TransactionType.PAYMENT;
        entity.externalPaymentKey = externalPaymentKey;
        entity.amount = amount;
        entity.message = message;
        entity.occurredAt = occurredAt;

        return entity;
    }

    public static TransactionHistoryEntity createFail(Long userId, Long orderId, Long paymentId, String code, String message) {
        TransactionHistoryEntity entity = new TransactionHistoryEntity();
        entity.userId = userId;
        entity.orderId = orderId;
        entity.paymentId = paymentId;
        entity.type = TransactionType.PAYMENT_FAIL;
        entity.externalPaymentKey = "";
        entity.amount = BigDecimal.valueOf(-1);
        entity.message = "[" + code + "] " + message;
        entity.occurredAt = LocalDateTime.now();

        return entity;
    }

    public static TransactionHistoryEntity createCancel(Long userId, Long orderId, Long paymentId, String externalPaymentKey, BigDecimal amount, LocalDateTime occurredAt) {
        TransactionHistoryEntity entity = new TransactionHistoryEntity();
        entity.userId = userId;
        entity.orderId = orderId;
        entity.paymentId = paymentId;
        entity.type = TransactionType.CANCEL;
        entity.externalPaymentKey = externalPaymentKey;
        entity.amount = amount;
        entity.message = "취소 성공";
        entity.occurredAt = occurredAt;

        return entity;
    }

}
