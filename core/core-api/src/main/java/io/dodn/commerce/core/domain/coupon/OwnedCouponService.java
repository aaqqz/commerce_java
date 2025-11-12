package io.dodn.commerce.core.domain.coupon;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.CouponType;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.storage.db.core.answer.AnswerEntity;
import io.dodn.commerce.storage.db.core.coupon.CouponEntity;
import io.dodn.commerce.storage.db.core.coupon.CouponRepository;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponEntity;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class OwnedCouponService {

    private final OwnedCouponFinder ownedCouponFinder;

    public List<OwnedCoupon> findOwnedCoupons(User user) {
        return ownedCouponFinder.findOwnedCoupons(user);
    }
}

