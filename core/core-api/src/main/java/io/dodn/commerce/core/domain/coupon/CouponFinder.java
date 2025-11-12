package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.enums.CouponTargetType;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.storage.db.core.coupon.CouponRepository;
import io.dodn.commerce.storage.db.core.coupon.CouponTargetEntity;
import io.dodn.commerce.storage.db.core.coupon.CouponTargetRepository;
import io.dodn.commerce.storage.db.core.product.ProductCategoryEntity;
import io.dodn.commerce.storage.db.core.product.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CouponFinder {

    private final CouponRepository couponRepository;
    private final CouponTargetRepository couponTargetRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Coupon> findCouponsForProducts(List<Long> productIds) {
        // PRODUCT 대상 쿠폰 조회
        List<CouponTargetEntity> productTargets = couponTargetRepository.findByTargetTypeAndTargetIdInAndStatus(
                CouponTargetType.PRODUCT,
                productIds,
                EntityStatus.ACTIVE
        );

        // PRODUCT_CATEGORY 대상 쿠폰 조회
        List<Long> categoryIds = productCategoryRepository.findByProductIdInAndStatus(productIds, EntityStatus.ACTIVE).stream()
                .map(ProductCategoryEntity::getCategoryId)
                .toList();

        List<CouponTargetEntity> categoryTargets = couponTargetRepository.findByTargetTypeAndTargetIdInAndStatus(
                CouponTargetType.PRODUCT_CATEGORY,
                categoryIds,
                EntityStatus.ACTIVE
        );

        // 두 리스트 합치고 couponId만 추출
        Set<Long> couponIds = Stream.concat(productTargets.stream(), categoryTargets.stream())
                .map(CouponTargetEntity::getCouponId)
                .collect(Collectors.toSet());

        // 최종 Coupon 조회 및 매핑
        return couponRepository.findByIdInAndStatus(couponIds, EntityStatus.ACTIVE).stream()
                .map(Coupon::of)
                .collect(Collectors.toList());
    }
}
