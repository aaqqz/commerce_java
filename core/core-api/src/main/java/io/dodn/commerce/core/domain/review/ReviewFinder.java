package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.storage.db.core.review.ReviewEntity;
import io.dodn.commerce.storage.db.core.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewFinder {

    private final ReviewRepository reviewRepository;

    public RateSummary findRateSummary(ReviewTarget target) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByTargetTypeAndTargetIdAndStatus(
                        target.type(), target.id(), EntityStatus.ACTIVE
                ).stream()
                .filter(ReviewEntity::isActive)
                .toList();

        if (reviewEntities.isEmpty()) {
            return RateSummary.EMPTY;
        } else {
            return RateSummary.of(reviewEntities);
        }
    }

    public Page<Review> find(ReviewTarget target, OffsetLimit offsetLimit) {
        Slice<ReviewEntity> reviewEntitySlice = reviewRepository.findByTargetTypeAndTargetIdAndStatus(
                target.type(),
                target.id(),
                EntityStatus.ACTIVE,
                offsetLimit.toPageable()
        );

        return new Page<>(
                reviewEntitySlice.getContent().stream()
                        .map(Review::of)
                        .toList(),
                reviewEntitySlice.hasNext()

        );
    }
}
