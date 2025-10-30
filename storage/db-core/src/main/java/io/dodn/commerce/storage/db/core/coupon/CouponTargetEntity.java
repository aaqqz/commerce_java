package io.dodn.commerce.storage.db.core.coupon;

import io.dodn.commerce.core.enums.CouponTargetType;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon_target")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTargetEntity extends BaseEntity {

    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private CouponTargetType targetType;

    private Long targetId;
}