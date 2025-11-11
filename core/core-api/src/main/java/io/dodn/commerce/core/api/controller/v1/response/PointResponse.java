package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.point.PointBalance;
import io.dodn.commerce.core.domain.point.PointHistory;
import io.dodn.commerce.core.enums.PointType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PointResponse (
        Long userId,
        BigDecimal balance,
        List<PointHistoryResponse> histories
) {
    public static PointResponse of(PointBalance balance, List<PointHistory> histories) {
        return new PointResponse(
                balance.userId(),
                balance.balance(),
                histories.stream()
                        .map(PointHistoryResponse::of)
                        .toList()
        );
    }
}

record PointHistoryResponse (
        PointType type,
        BigDecimal amount,
        LocalDateTime appliedAt
) {
    public static PointHistoryResponse of(PointHistory history) {
        return new PointHistoryResponse(history.type(), history.amount(), history.appliedAt());
    }
}