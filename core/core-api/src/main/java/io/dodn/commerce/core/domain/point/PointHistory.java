package io.dodn.commerce.core.domain.point;

import io.dodn.commerce.core.enums.PointType;
import io.dodn.commerce.storage.db.core.point.PointHistoryEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PointHistory(
        Long id,
        Long userId,
        PointType type,
        Long referenceId,
        BigDecimal amount,
        LocalDateTime appliedAt
) {
    public static PointHistory from(PointHistoryEntity entity) {
        return new PointHistory(
                entity.getId(),
                entity.getUserId(),
                entity.getType(),
                entity.getReferenceId(),
                entity.getAmount(),
                entity.getCreatedAt()
        );
    }
}
