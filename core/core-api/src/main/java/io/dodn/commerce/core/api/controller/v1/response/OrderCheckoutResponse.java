package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.coupon.OwnedCoupon;
import io.dodn.commerce.core.domain.order.Order;
import io.dodn.commerce.core.domain.point.PointBalance;

import java.math.BigDecimal;
import java.util.List;

public record OrderCheckoutResponse(
        String key,
        String name,
        BigDecimal totalPrice,
        List<OrderItemResponse> items,
        List<OwnedCouponResponse> usableCoupons,
        BigDecimal usablePoint
) {
    public static OrderCheckoutResponse of(Order order, List<OwnedCoupon> ownedCoupons, PointBalance pointBalance) {
        return new OrderCheckoutResponse(
                order.key(),
                order.name(),
                order.totalPrice(),
                OrderItemResponse.of(order.items()),
                OwnedCouponResponse.of(ownedCoupons),
                pointBalance.balance()
        );
    }
}
