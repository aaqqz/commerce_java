package io.dodn.commerce.storage.db.core.settlement;

import io.dodn.commerce.core.enums.SettlementState;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(
        name = "settlement",
        indexes = {
                @Index(name = "udx_settlement_merchant", columnList = "settlementDate, merchantId", unique = true)
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementEntity extends BaseEntity {
    private Long merchantId;
    private LocalDate settlementDate;
    private BigDecimal originalAmount;
    private BigDecimal feeAmount;
    private BigDecimal feeRate;
    private BigDecimal settlementAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private SettlementState state;

    public static SettlementEntity create(Long merchantId, LocalDate settlementDate, BigDecimal originalAmount, BigDecimal feeAmount, BigDecimal feeRate, BigDecimal settlementAmount) {
        SettlementEntity entity = new SettlementEntity();
        entity.merchantId = merchantId;
        entity.settlementDate = settlementDate;
        entity.originalAmount = originalAmount;
        entity.feeAmount = feeAmount;
        entity.feeRate = feeRate;
        entity.settlementAmount = settlementAmount;
        entity.state = SettlementState.READY;

        return entity;
    }

    public void sent() {
        state = SettlementState.SENT;
    }
}