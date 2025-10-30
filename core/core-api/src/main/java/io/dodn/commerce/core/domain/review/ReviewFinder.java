package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.storage.db.core.review.ReviewEntity;
import io.dodn.commerce.storage.db.core.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewFinder {

    private final ReviewRepository reviewRepository;

    public RateSummary findRateSummary(ReviewTarget target) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByTargetTypeAndTargetId(target.type(), target.id()).stream()
                .filter(ReviewEntity::isActive)
                .toList();

        if (reviewEntities.isEmpty()) {
            return RateSummary.EMPTY;
        } else {
            return RateSummary.of(reviewEntities);
        }
    }
}
