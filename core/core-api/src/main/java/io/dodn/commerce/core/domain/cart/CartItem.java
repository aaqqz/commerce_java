package io.dodn.commerce.core.domain.cart;

import io.dodn.commerce.core.domain.product.Product;

public record CartItem(
        Long id,
        Product product,
        Long quantity
) {
}
