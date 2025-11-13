package io.dodn.commerce.core.domain.cart;

import java.util.List;

public record Cart(
        Long userId,
        List<CartItem> items
) {
}
