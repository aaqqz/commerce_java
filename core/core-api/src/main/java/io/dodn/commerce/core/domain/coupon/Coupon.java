package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.enums.CouponType;
import io.dodn.commerce.storage.db.core.coupon.CouponEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Coupon(
        Long id,
        String name,
        CouponType type,
        BigDecimal discount,
        LocalDateTime expiredAt
) {

    public static Coupon of(CouponEntity couponEntity) {
        return new Coupon(couponEntity.getId(), couponEntity.getName(), couponEntity.getType(), couponEntity.getDiscount(), couponEntity.getExpiredAt());
    }
}
