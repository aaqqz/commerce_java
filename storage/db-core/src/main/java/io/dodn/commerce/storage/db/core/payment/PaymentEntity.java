package io.dodn.commerce.storage.db.core.payment;

import io.dodn.commerce.core.enums.PaymentMethod;
import io.dodn.commerce.core.enums.PaymentState;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "payment",
        indexes = {
                @Index(name = "udx_order_id", columnList = "orderId", unique = true)
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {

    private Long userId;
    private Long orderId;
    private BigDecimal originAmount;
    private Long ownedCouponId;
    private BigDecimal couponDiscount;
    private BigDecimal usedPoint;
    private BigDecimal paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private PaymentState state;

    // 결제 후
    private String externalPaymentKey;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private PaymentMethod method;
    private String approveCode;
    private LocalDateTime paidAt;

    public static PaymentEntity of(Long userId, Long orderId, BigDecimal originAmount, Long ownedCouponId, BigDecimal couponDiscount, BigDecimal usedPoint, BigDecimal paidAmount, PaymentState state) {
        PaymentEntity entity = new PaymentEntity();
        entity.userId = userId;
        entity.orderId = orderId;
        entity.originAmount = originAmount;
        entity.ownedCouponId = ownedCouponId;
        entity.couponDiscount = couponDiscount;
        entity.usedPoint = usedPoint;
        entity.paidAmount = paidAmount;
        entity.state = state;

        return entity;
    }

    public void success(String externalPaymentKey, PaymentMethod method, String approveCode) {
        this.externalPaymentKey = externalPaymentKey;
        this.method = method;
        this.approveCode = approveCode;
        this.paidAt = LocalDateTime.now();
        this.state = PaymentState.SUCCESS;
    }

    public boolean hasAppliedCoupon() {
        return ownedCouponId > 0;
    }
}
