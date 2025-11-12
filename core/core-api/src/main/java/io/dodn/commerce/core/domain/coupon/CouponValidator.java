package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.coupon.CouponRepository;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponEntity;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponValidator {

    private final CouponRepository couponRepository;
    private final OwnedCouponRepository ownedCouponRepository;

    public void validateDownload(User user, Long couponId) {
        couponRepository.findByIdAndStatusAndExpiredAtAfter(couponId, EntityStatus.ACTIVE, LocalDateTime.now())
                .orElseThrow(() -> new CoreException(ErrorType.COUPON_NOT_FOUND_OR_EXPIRED));

        Optional<OwnedCouponEntity> existing = ownedCouponRepository.findByUserIdAndCouponId(user.id(), couponId);
        if (existing.isPresent()) {
            throw new CoreException(ErrorType.COUPON_ALREADY_DOWNLOADED);
        }
    }
}
