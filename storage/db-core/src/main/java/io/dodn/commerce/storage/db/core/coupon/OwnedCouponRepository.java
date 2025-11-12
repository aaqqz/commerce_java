package io.dodn.commerce.storage.db.core.coupon;

import io.dodn.commerce.core.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnedCouponRepository extends JpaRepository<OwnedCouponEntity, Long> {

    Optional<OwnedCouponEntity> findByUserIdAndCouponId(Long userId, Long couponId);

    List<OwnedCouponEntity> findByUserIdAndStatus(Long userId, EntityStatus status);
}
