package io.dodn.commerce.storage.db.core.coupon;

import io.dodn.commerce.core.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    List<CouponEntity> findByIdInAndStatus(Set<Long> ids, EntityStatus status);

    Optional<CouponEntity> findByIdAndStatusAndExpiredAtAfter(Long couponId, EntityStatus status, LocalDateTime now);
}
