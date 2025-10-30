package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.storage.db.core.review.ReviewEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record RateSummary(
        BigDecimal rate,
        Long count
) {
    public static final RateSummary EMPTY = new RateSummary(BigDecimal.ZERO, 0L);

    public static RateSummary of(List<ReviewEntity> reviewEntities) {
        if (reviewEntities.isEmpty()) {
            return EMPTY;
        }

        BigDecimal rate = reviewEntities.stream()
                .map(ReviewEntity::getRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(reviewEntities.size()), RoundingMode.HALF_UP);

        return new RateSummary(rate, (long) reviewEntities.size());
    }
}
