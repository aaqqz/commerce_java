package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.review.ReviewEntity;
import io.dodn.commerce.storage.db.core.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReviewManager {

    private final ReviewRepository reviewRepository;

    public Long add(ReviewKey reviewKey, ReviewTarget target, ReviewContent content) {
        ReviewEntity saved = reviewRepository.save(
                new ReviewEntity(
                        reviewKey.user().id(),
                        reviewKey.key(),
                        target.type(),
                        target.id(),
                        content.rate(),
                        content.content()
                )
        );

        return saved.getId();
    }

    @Transactional
    public Long update(User user, Long reviewId, ReviewContent content) {
        ReviewEntity reviewEntity = reviewRepository.findByIdAndUserId(reviewId, user.id())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        reviewEntity.updateContent(content.rate(), content.content());

        return reviewEntity.getId();
    }
}
