package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.core.domain.point.PointAmount;
import io.dodn.commerce.core.domain.point.PointHandler;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.PointType;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewFinder reviewFinder;
    private final ReviewPolicyValidator reviewPolicyValidator;
    private final ReviewManager reviewManager;
    private final PointHandler pointHandler;

    public RateSummary findRateSummary(ReviewTarget target) {
        return reviewFinder.findRateSummary(target);
    }

    public Page<Review> findReviews(ReviewTarget target, OffsetLimit offsetLimit) {
        return reviewFinder.find(target, offsetLimit);
    }

    public Long createReview(User user, ReviewTarget target, ReviewContent content) {
        ReviewKey reviewKey = reviewPolicyValidator.validateCreate(user, target);
        Long reviewId = reviewManager.create(reviewKey, target, content);

        pointHandler.earn(user, PointType.REVIEW, reviewId, PointAmount.REVIEW);
        return reviewId;
    }

    public Long updateReview(User user, Long reviewId, ReviewContent content) {
        reviewPolicyValidator.validateUpdate(user, reviewId);
        return reviewManager.update(user, reviewId, content);
    }

    public Long deleteReview(User user, Long reviewId) {
        Long deletedReviewId = reviewManager.delete(user, reviewId);

        pointHandler.deduct(user, PointType.REVIEW, deletedReviewId, PointAmount.REVIEW);
        return deletedReviewId;
    }
}