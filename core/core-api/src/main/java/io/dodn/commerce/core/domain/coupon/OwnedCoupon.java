package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.enums.OwnedCouponState;

public record OwnedCoupon(
        Long id,
        Long userId,
        OwnedCouponState state,
        Coupon coupon
) {
}
