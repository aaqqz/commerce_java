package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.order.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemResponse(
        Long productId,
        String productName,
        String thumbnailUrl,
        String shortDescription,
        Long quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
    public static OrderItemResponse of(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.productId(),
                orderItem.productName(),
                orderItem.thumbnailUrl(),
                orderItem.shortDescription(),
                orderItem.quantity(),
                orderItem.unitPrice(),
                orderItem.totalPrice()
        );
    }

    public static List<OrderItemResponse> of(List<OrderItem> items) {
        return items.stream()
                .map(OrderItemResponse::of)
                .toList();
    }
}
