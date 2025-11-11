package io.dodn.commerce.storage.db.core.point;

import io.dodn.commerce.core.enums.PointType;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "point_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistoryEntity extends BaseEntity {

    private Long userId;
    private PointType type;
    private Long referenceId;
    private BigDecimal amount;
    private BigDecimal balanceAfter;

    public static PointHistoryEntity of(Long userId, PointType type, Long referenceId, BigDecimal amount, BigDecimal balanceAfter) {
        PointHistoryEntity entity = new PointHistoryEntity();

        entity.userId = userId;
        entity.type = type;
        entity.referenceId = referenceId;
        entity.amount = amount;
        entity.balanceAfter = balanceAfter;

        return entity;
    }
}
