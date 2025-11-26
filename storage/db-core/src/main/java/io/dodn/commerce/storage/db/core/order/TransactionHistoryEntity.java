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

    Long userId;
    Long orderId;
    Long paymentId;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    TransactionType type;

    String externalPaymentKey;
    BigDecimal amount;
    String message;
    LocalDateTime occurredAt;
}
