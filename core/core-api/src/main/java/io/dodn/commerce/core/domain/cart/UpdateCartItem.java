package io.dodn.commerce.core.domain.cart;

public record UpdateCartItem(
        Long cartItemId,
        Long quantity
) {
}
