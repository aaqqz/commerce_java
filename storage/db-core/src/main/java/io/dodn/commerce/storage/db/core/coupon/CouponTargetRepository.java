package io.dodn.commerce.storage.db.core.coupon;

import io.dodn.commerce.core.enums.CouponTargetType;
import io.dodn.commerce.core.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponTargetRepository extends JpaRepository<CouponTargetEntity, Long> {

    List<CouponTargetEntity> findByTargetTypeAndTargetIdInAndStatus(CouponTargetType target, List<Long> targetIds, EntityStatus status);
}
