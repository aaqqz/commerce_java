package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.product.Product;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        String name,
        String thumbnailUrl,
        String description,
        String shortDescription,
        BigDecimal costPrice,
        BigDecimal salesPrice,
        BigDecimal discountedPrice
) {

    public static List<ProductResponse> of(List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(
                        product.name(),
                        product.thumbnailUrl(),
                        product.description(),
                        product.shortDescription(),
                        product.price().costPrice(),
                        product.price().salesPrice(),
                        product.price().discountedPrice()
                ))
                .toList();
    }
}
