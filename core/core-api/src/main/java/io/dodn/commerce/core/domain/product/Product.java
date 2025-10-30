package io.dodn.commerce.core.domain.product;

import io.dodn.commerce.storage.db.core.product.ProductEntity;

public record Product(
        Long id,
        String name,
        String thumbnailUrl,
        String description,
        String shortDescription,
        Price price
) {

    public static Product of(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getThumbnailUrl(),
                productEntity.getDescription(),
                productEntity.getShortDescription(),
                Price.of(productEntity)
        );
    }
}