package io.dodn.commerce.core.domain.product;

import io.dodn.commerce.storage.db.core.product.ProductEntity;

import java.math.BigDecimal;

public record Price(
        BigDecimal costPrice,
        BigDecimal salesPrice,
        BigDecimal discountedPrice
) {

    public static Price of(ProductEntity productEntity) {
        return new Price(
                productEntity.getCostPrice(),
                productEntity.getSalesPrice(),
                productEntity.getDiscountedPrice()
        );
    }
}
