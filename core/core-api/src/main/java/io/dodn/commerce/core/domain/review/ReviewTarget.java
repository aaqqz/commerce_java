package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.core.enums.ReviewTargetType;

public record ReviewTarget(
        ReviewTargetType type,
        Long id
) {

    public static ReviewTarget of(ReviewTargetType type, Long id) {
        return new ReviewTarget(type, id);
    }
}
