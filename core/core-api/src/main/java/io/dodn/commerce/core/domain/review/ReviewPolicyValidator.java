package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.enums.ReviewTargetType;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.order.OrderItemEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemRepository;
import io.dodn.commerce.storage.db.core.review.ReviewEntity;
import io.dodn.commerce.storage.db.core.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.dodn.commerce.core.domain.review.ReviewPolicyConstants.*;

@Component
@RequiredArgsConstructor
public class ReviewPolicyValidator {

    private final OrderItemRepository orderItemRepository;
    private final ReviewRepository reviewRepository;

    public ReviewKey validateNew(User user, ReviewTarget target) {
        if (target.type() == ReviewTargetType.PRODUCT) {
            List<OrderItemEntity> orderItemEntities = orderItemRepository.findRecentOrderItemsForProduct(
                    user.id(),
                    target.id(),
                    OrderState.PAID,
                    LocalDateTime.now().minusDays(REVIEW_CREATION_AVAILABLE_DAYS),
                    EntityStatus.ACTIVE
            );
            List<String> reviewKeys = orderItemEntities.stream()
                    .map(entity -> generateReviewKey(entity.getId()))
                    .toList();

            Set<String> existReviewKeys = reviewRepository.findByUserIdAndReviewKeyIn(user.id(), reviewKeys).stream()
                    .map(ReviewEntity::getReviewKey)
                    .collect(Collectors.toSet());

            String availableKey = reviewKeys.stream()
                    .filter(key -> !existReviewKeys.contains(key))
                    .findFirst()
                    .orElseThrow(() -> new CoreException(ErrorType.REVIEW_HAS_NOT_ORDER));

            return new ReviewKey(user, availableKey);
        }

        throw new UnsupportedOperationException();
    }

    public void validateUpdate(User user, Long reviewId) {
        ReviewEntity reviewEntity = reviewRepository.findByIdAndUserId(reviewId, user.id())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));

        if (reviewEntity.getCreatedAt()
                .plusDays(REVIEW_UPDATE_AVAILABLE_DAYS)
                .isBefore(LocalDateTime.now())
        ) {
            throw new CoreException(ErrorType.REVIEW_UPDATE_EXPIRED);
        }
    }
}