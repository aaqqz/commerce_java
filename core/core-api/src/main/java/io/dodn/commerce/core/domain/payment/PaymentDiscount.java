package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.core.domain.coupon.OwnedCoupon;
import io.dodn.commerce.core.domain.point.PointBalance;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record PaymentDiscount(
        List<OwnedCoupon> ownedCoupons,
        PointBalance pointBalance,
        Long useOwnedCouponId,
        BigDecimal usePointAmount,
        BigDecimal couponDiscount,
        BigDecimal usePoint
) {

    public PaymentDiscount(List<OwnedCoupon> ownedCoupons,
                           PointBalance pointBalance,
                           Long useOwnedCouponId,
                           BigDecimal usePointAmount
    ) {

        this(
                ownedCoupons,
                pointBalance,
                useOwnedCouponId,
                usePointAmount,
                calculateCouponDiscount(ownedCoupons, useOwnedCouponId),
                calculateUsePoint(usePointAmount, pointBalance)
        );
    }

    private static BigDecimal calculateCouponDiscount(List<OwnedCoupon> ownedCoupons, Long useOwnedCouponId) {
        // 쿠폰 할인 계산
        if (useOwnedCouponId <= 0) {
            return BigDecimal.ZERO;
        }

        OwnedCoupon ownedCoupon = ownedCoupons.stream()
                .filter(it -> Objects.equals(it.id(), useOwnedCouponId))
                .findFirst()
                .orElseThrow(() -> new CoreException(ErrorType.OWNED_COUPON_INVALID));

        return ownedCoupon.coupon().discount();
    }

    private static BigDecimal calculateUsePoint(BigDecimal usePointAmount, PointBalance pointBalance) {
        // 포인트 사용액 계산
        if (usePointAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        if (usePointAmount.compareTo(pointBalance.balance()) > 0) {
            throw new CoreException(ErrorType.POINT_EXCEEDS_BALANCE);
        }

        return usePointAmount;
    }

    public BigDecimal paidAmount(BigDecimal orderPrice) {
        BigDecimal finalAmount = orderPrice.subtract(couponDiscount.add(usePointAmount));

        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new CoreException(ErrorType.PAYMENT_INVALID_AMOUNT);
        }

        return finalAmount;
    }
}
