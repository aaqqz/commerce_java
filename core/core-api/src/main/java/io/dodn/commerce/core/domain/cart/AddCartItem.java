package io.dodn.commerce.core.domain.cart;

public record AddCartItem(
        Long productId,
        Long quantity
) {
}
