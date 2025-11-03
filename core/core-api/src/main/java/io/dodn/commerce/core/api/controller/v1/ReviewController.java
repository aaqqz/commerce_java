package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.request.AddReviewRequest;
import io.dodn.commerce.core.api.controller.v1.request.UpdateReviewRequest;
import io.dodn.commerce.core.api.controller.v1.response.ReviewResponse;
import io.dodn.commerce.core.domain.review.Review;
import io.dodn.commerce.core.domain.review.ReviewService;
import io.dodn.commerce.core.domain.review.ReviewTarget;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.ReviewTargetType;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.core.support.response.ApiResponse;
import io.dodn.commerce.core.support.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/v1/reviews")
    public ApiResponse<PageResponse<ReviewResponse>> getReviews(
            @RequestParam ReviewTargetType targetType,
            @RequestParam Long targetId,
            @RequestParam Integer offset,
            @RequestParam Integer limit
    ) {
        Page<Review> reviews = reviewService.findReviews(ReviewTarget.of(targetType, targetId), OffsetLimit.of(offset, limit));
        return ApiResponse.success(new PageResponse<>(ReviewResponse.of(reviews.content()), reviews.hasNext()));
    }

    @PostMapping("/v1/reviews")
    public ApiResponse<Object> addReview(
            User user,
            @RequestBody AddReviewRequest request
    ) {
        reviewService.addReview(user, request.toTarget(), request.toContent());
        return ApiResponse.success();
    }

    @PutMapping("/v1/reviews/{reviewId}")
    public ApiResponse<Object> updateReview(User user, @PathVariable Long reviewId, @RequestBody UpdateReviewRequest request) {
        reviewService.updateReview(user, reviewId, request.toContent());
        return ApiResponse.success();
    }
}
