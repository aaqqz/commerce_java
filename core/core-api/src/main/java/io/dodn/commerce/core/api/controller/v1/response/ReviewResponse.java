package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.review.Review;
import io.dodn.commerce.core.enums.ReviewTargetType;

import java.math.BigDecimal;
import java.util.List;

public record ReviewResponse(
        Long id,
        ReviewTargetType targetType,
        Long targetId,
        BigDecimal rate,
        String content
) {

    public static ReviewResponse of(Review review) {
        return new ReviewResponse(
                review.id(),
                review.target().type(),
                review.target().id(),
                review.content().rate(),
                review.content().content()
        );
    }

    public static List<ReviewResponse> of(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResponse::of)
                .toList();
    }
}