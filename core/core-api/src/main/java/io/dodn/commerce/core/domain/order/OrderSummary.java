package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.storage.db.core.order.OrderEntity;

import java.math.BigDecimal;

public record OrderSummary(
        Long id,
        String key,
        String name,
        Long userId,
        BigDecimal totalPrice,
        OrderState state
) {

    public static OrderSummary of(OrderEntity order, User user) {
        return new OrderSummary(
                order.getId(),
                order.getOrderKey(),
                order.getName(),
                user.id(),
                order.getTotalPrice(),
                order.getState()
        );
    }
}
