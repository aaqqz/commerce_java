package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.storage.db.core.coupon.CouponEntity;
import io.dodn.commerce.storage.db.core.coupon.CouponRepository;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponEntity;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class OwnedCouponFinder {

    private final OwnedCouponRepository ownedCouponRepository;
    private final CouponRepository couponRepository;

    public List<OwnedCoupon> findOwnedCoupons(User user) {
        List<OwnedCouponEntity> ownedCouponEntities = ownedCouponRepository.findByUserIdAndStatus(user.id(), EntityStatus.ACTIVE);
        if (ownedCouponEntities.isEmpty()) return emptyList();

        Set<Long> ownedCouponIds = ownedCouponEntities.stream()
                .map(OwnedCouponEntity::getCouponId)
                .collect(Collectors.toSet());

        List<CouponEntity> couponEntities = couponRepository.findAllById(ownedCouponIds);
        Map<Long, CouponEntity> couponEntityMap = couponEntities.stream()
                .collect(Collectors.toMap(
                        CouponEntity::getId,
                        coupon -> coupon
                ));

        // map get .pop null 처리
        return ownedCouponEntities.stream()
                .map(ownedCouponEntity ->
                        new OwnedCoupon(
                                ownedCouponEntity.getId(),
                                ownedCouponEntity.getUserId(),
                                ownedCouponEntity.getState(),
                                new Coupon(
                                        couponEntityMap.get(ownedCouponEntity.getCouponId()).getId(),
                                        couponEntityMap.get(ownedCouponEntity.getCouponId()).getName(),
                                        couponEntityMap.get(ownedCouponEntity.getCouponId()).getType(),
                                        couponEntityMap.get(ownedCouponEntity.getCouponId()).getDiscount(),
                                        couponEntityMap.get(ownedCouponEntity.getCouponId()).getExpiredAt()
                                )
                        ))
                .toList();
    }

    public List<OwnedCoupon> getOwnedCouponsForCheckout(User user, List<Long> productIds) {
        if (productIds.isEmpty()) return emptyList();

        Map<Long, CouponEntity> applicableCouponMap = couponRepository.findApplicableCouponIds(productIds).stream()
                .collect(Collectors.toMap(
                        CouponEntity::getId,
                        coupon -> coupon
                ));
        if (applicableCouponMap.isEmpty()) return emptyList();

        var ownedCoupons = ownedCouponRepository.findOwnedCouponIds(user.id(), applicableCouponMap.keySet(), LocalDateTime.now());
        if (ownedCoupons.isEmpty()) return emptyList();

        return ownedCoupons.stream()
                .map(ownedCouponEntity ->
                        new OwnedCoupon(
                                ownedCouponEntity.getId(),
                                ownedCouponEntity.getUserId(),
                                ownedCouponEntity.getState(),
                                new Coupon(
                                        applicableCouponMap.get(ownedCouponEntity.getCouponId()).getId(),
                                        applicableCouponMap.get(ownedCouponEntity.getCouponId()).getName(),
                                        applicableCouponMap.get(ownedCouponEntity.getCouponId()).getType(),
                                        applicableCouponMap.get(ownedCouponEntity.getCouponId()).getDiscount(),
                                        applicableCouponMap.get(ownedCouponEntity.getCouponId()).getExpiredAt()
                                )
                        ))
                .toList();
    }
}
