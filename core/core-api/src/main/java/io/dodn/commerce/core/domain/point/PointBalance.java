package io.dodn.commerce.core.domain.point;

import java.math.BigDecimal;

public record PointBalance(
        Long userId,
        BigDecimal balance
) {
}
