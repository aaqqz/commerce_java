package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.storage.db.core.review.ReviewEntity;

public record Review(
        Long id,
        Long userId,
        ReviewTarget target,
        ReviewContent content
) {

    public static Review of(ReviewEntity entity) {
        return new Review(
                entity.getId(),
                entity.getUserId(),
                ReviewTarget.of(
                        entity.getTargetType(),
                        entity.getTargetId()
                ),
                new ReviewContent(
                        entity.getRate(),
                        entity.getContent()
                )
        );
    }
}
