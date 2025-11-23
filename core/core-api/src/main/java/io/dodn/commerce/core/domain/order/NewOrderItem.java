package io.dodn.commerce.core.domain.order;

public record NewOrderItem(
        Long productId,
        Long quantity
) {
}
