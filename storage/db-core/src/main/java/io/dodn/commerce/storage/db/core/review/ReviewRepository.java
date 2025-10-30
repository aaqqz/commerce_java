package io.dodn.commerce.storage.db.core.review;

import io.dodn.commerce.core.enums.ReviewTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByTargetTypeAndTargetId(ReviewTargetType target, Long targetId);
}
