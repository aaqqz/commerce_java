package io.dodn.commerce.storage.db.core.review;

import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.enums.ReviewTargetType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByTargetTypeAndTargetIdAndStatus(ReviewTargetType target, Long targetId, EntityStatus status);

    Slice<ReviewEntity> findByTargetTypeAndTargetIdAndStatus(ReviewTargetType target, Long targetId, EntityStatus status, Pageable pageable);

    List<ReviewEntity> findByUserIdAndReviewKeyIn(Long userId, List<String> reviewKeys);

    Optional<ReviewEntity> findByIdAndUserId(Long reviewId, Long userId);
}
