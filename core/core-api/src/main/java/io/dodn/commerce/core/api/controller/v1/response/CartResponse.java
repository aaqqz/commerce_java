package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.cart.CartItem;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse (
        List<CartItemResponse> items
) {

    public static CartResponse of(List<CartItem> items) {
        return new CartResponse(items.stream()
                .map(CartItemResponse::of)
                .toList()
        );
    }
}

record  CartItemResponse(
        Long id,
        Long productId,
        String productName,
        String thumbnailUrl,
        String description,
        String shortDescription,
        BigDecimal costPrice,
        BigDecimal salesPrice,
        BigDecimal discountedPrice,
        Long quantity
) {

    public static CartItemResponse of(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.id(),
                cartItem.product().id(),
                cartItem.product().name(),
                cartItem.product().thumbnailUrl(),
                cartItem.product().description(),
                cartItem.product().shortDescription(),
                cartItem.product().price().costPrice(),
                cartItem.product().price().salesPrice(),
                cartItem.product().price().discountedPrice(),
                cartItem.quantity()

        );
    }
}