package io.dodn.commerce.core.domain.review;

import java.math.BigDecimal;

public record ReviewContent(
        BigDecimal rate,
        String content
) {
}
