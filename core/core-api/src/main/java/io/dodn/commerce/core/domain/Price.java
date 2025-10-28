package io.dodn.commerce.core.domain;

import io.dodn.commerce.storage.db.core.ProductEntity;

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
