package io.dodn.commerce.storage.db.core.settlement;

import io.dodn.commerce.core.enums.TransactionType;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "settlement_target")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementTargetEntity extends BaseEntity {

    private Long merchantId;
    private LocalDate settlementDate;
    private BigDecimal targetAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private TransactionType transactionType;

    Long transactionId;
    Long orderId;
    Long productId;
    Long quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;

    public static SettlementTargetEntity create(
        Long merchantId,
        LocalDate settlementDate,
        BigDecimal targetAmount,
        TransactionType transactionType,
        Long transactionId,
        Long orderId,
        Long productId,
        Long quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
    ) {
        SettlementTargetEntity entity = new SettlementTargetEntity();
        entity.merchantId = merchantId;
        entity.settlementDate = settlementDate;
        entity.targetAmount = targetAmount;
        entity.transactionType = transactionType;
        entity.transactionId = transactionId;
        entity.orderId = orderId;
        entity.productId = productId;
        entity.quantity = quantity;
        entity.unitPrice = unitPrice;
        entity.totalPrice = totalPrice;

        return entity;
    }
}
