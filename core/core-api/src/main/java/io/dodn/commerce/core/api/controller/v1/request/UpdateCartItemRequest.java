package io.dodn.commerce.core.api.controller.v1.request;

import io.dodn.commerce.core.domain.cart.UpdateCartItem;

public record UpdateCartItemRequest(
        Long quantity
) {
    public UpdateCartItem toUpdateCartItem(Long cartItemId) {
        return new UpdateCartItem(cartItemId, quantity);
    }
}
