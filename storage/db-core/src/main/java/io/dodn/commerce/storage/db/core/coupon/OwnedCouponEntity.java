package io.dodn.commerce.storage.db.core.coupon;

import io.dodn.commerce.core.enums.OwnedCouponState;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(
        name = "owned_coupon",
        indexes = {
                @Index(name = "udx_owned_coupon", columnList = "userId, couponId", unique = true),
        }
)
public class OwnedCouponEntity extends BaseEntity {

    private Long userId;
    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private OwnedCouponState state;

    @Version
    private Long version = 0L;

    public void  use() {
        this.state = OwnedCouponState.USED;
    }

    public void revert() {
        this.state = OwnedCouponState.DOWNLOADED;
    }
}
