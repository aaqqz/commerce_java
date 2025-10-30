package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.coupon.Coupon;
import io.dodn.commerce.core.enums.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(
        Long id,
        String name,
        CouponType type,
        BigDecimal discount,
        LocalDateTime expiredAt
) {

    public static CouponResponse of(Coupon coupon) {
        return new CouponResponse(
                coupon.id(),
                coupon.name(),
                coupon.type(),
                coupon.discount(),
                coupon.expiredAt()
        );
    }
}
