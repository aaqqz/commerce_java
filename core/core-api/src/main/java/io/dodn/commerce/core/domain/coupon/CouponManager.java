package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponEntity;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponManager {

    private final OwnedCouponRepository  ownedCouponRepository;

    public void create(User user, Long couponId) {
        ownedCouponRepository.save(
                OwnedCouponEntity.of(user.id(), couponId)
        );
    }
}
