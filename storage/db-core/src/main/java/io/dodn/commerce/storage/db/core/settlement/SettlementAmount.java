package io.dodn.commerce.storage.db.core.settlement;

import java.math.BigDecimal;

public record SettlementAmount(
        BigDecimal originalAmount,
        BigDecimal feeAmount,
        BigDecimal feeRate,
        BigDecimal settlementAmount
) {
}
