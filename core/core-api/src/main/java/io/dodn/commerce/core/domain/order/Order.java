package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemEntity;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        Long id,
        String key,
        String name,
        Long userId,
        BigDecimal totalPrice,
        OrderState state,
        List<OrderItem> items
) {
    public static Order of(OrderEntity order, User user, List<OrderItemEntity> items) {
        return new Order(
                order.getId(),
                order.getOrderKey(),
                order.getName(),
                user.id(),
                order.getTotalPrice(),
                order.getState(),
                items.stream()
                        .map(it -> new OrderItem(
                                order.getId(),
                                it.getProductId(),
                                it.getProductName(),
                                it.getThumbnailUrl(),
                                it.getShortDescription(),
                                it.getQuantity(),
                                it.getUnitPrice(),
                                it.getTotalPrice()
                        ))
                        .toList()
        );
    }
}
