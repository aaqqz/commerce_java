package io.dodn.commerce.storage.db.core.payment;

import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "cancel")
public class CancelEntity extends BaseEntity {
    private Long userId;
    private Long orderId;
    private Long paymentId;
    private BigDecimal originAmount;
    private Long ownedCouponId;
    private BigDecimal couponDiscount;
    private BigDecimal usedPoint;
    private BigDecimal paidAmount;
    private BigDecimal canceledAmount;
    private String externalCancelKey;
    private LocalDateTime canceledAt;

    public static CancelEntity create(Long userId, Long orderId, Long paymentId, BigDecimal originAmount, Long ownedCouponId, BigDecimal couponDiscount, BigDecimal usedPoint, BigDecimal paidAmount, BigDecimal canceledAmount, String externalCancelKey) {
        CancelEntity entity = new CancelEntity();
        entity.userId = userId;
        entity.orderId = orderId;
        entity.paymentId = paymentId;
        entity.originAmount = originAmount;
        entity.ownedCouponId = ownedCouponId;
        entity.couponDiscount = couponDiscount;
        entity.usedPoint = usedPoint;
        entity.paidAmount = paidAmount;
        entity.canceledAmount = canceledAmount;
        entity.externalCancelKey = externalCancelKey;
        entity.canceledAt = LocalDateTime.now();

        return entity;
    }
}
