package io.dodn.commerce.storage.db.core.settlement;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SettlementCalculator {

    private static final BigDecimal FEE = BigDecimal.valueOf(0.1);

    private SettlementCalculator() {
        // 유틸 클래스이므로 인스턴스 생성 금지
    }

    public static SettlementAmount calculate(BigDecimal amount) {
        BigDecimal feeAmount = amount.multiply(FEE).setScale(2, RoundingMode.HALF_UP);

        return new SettlementAmount(
                amount,
                feeAmount,
                FEE,
                amount.subtract(feeAmount).setScale(2, RoundingMode.HALF_UP)
        );
    }
}