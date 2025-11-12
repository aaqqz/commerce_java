package io.dodn.commerce.storage.db.core.coupon;

import io.dodn.commerce.core.enums.OwnedCouponState;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
        name = "owned_coupon",
        indexes = {
                @Index(name = "udx_owned_coupon", columnList = "userId, couponId", unique = true),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedCouponEntity extends BaseEntity {

    private Long userId;
    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private OwnedCouponState state;

    @Version
    private Long version = 0L;

    public static OwnedCouponEntity of(Long userId, Long couponId) {
        OwnedCouponEntity entity = new OwnedCouponEntity();

        entity.userId = userId;
        entity.couponId = couponId;
        entity.state = OwnedCouponState.DOWNLOADED;

        return entity;
    }

    public void  use() {
        this.state = OwnedCouponState.USED;
    }

    public void revert() {
        this.state = OwnedCouponState.DOWNLOADED;
    }
}
