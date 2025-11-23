package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnedCouponService {

    private final OwnedCouponFinder ownedCouponFinder;

    public List<OwnedCoupon> findOwnedCoupons(User user) {
        return ownedCouponFinder.findOwnedCoupons(user);
    }

    public List<OwnedCoupon> getOwnedCouponsForCheckout(User user, List<Long> productIds) {
        return ownedCouponFinder.getOwnedCouponsForCheckout(user, productIds);
    }
}

