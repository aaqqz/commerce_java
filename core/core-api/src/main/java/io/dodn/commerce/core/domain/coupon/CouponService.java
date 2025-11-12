package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.CouponTargetType;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.storage.db.core.coupon.*;
import io.dodn.commerce.storage.db.core.product.ProductCategoryEntity;
import io.dodn.commerce.storage.db.core.product.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponFinder couponFinder;
    private final CouponValidator couponValidator;
    private final CouponManager couponManager;

    public List<Coupon> findCouponsForProducts(List<Long> productIds) {
        return couponFinder.findCouponsForProducts(productIds);
    }

    public void download(User user, Long couponId) {
        couponValidator.validateDownload(user, couponId);
        couponManager.create(user, couponId);
    }
}