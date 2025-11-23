package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.order.Order;
import io.dodn.commerce.core.enums.OrderState;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        String key,
        String name,
        BigDecimal totalPrice,
        OrderState state,
        List<OrderItemResponse> items
) {
    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.key(),
                order.name(),
                order.totalPrice(),
                order.state(),
                OrderItemResponse.of(order.items())
        );
    }
}
