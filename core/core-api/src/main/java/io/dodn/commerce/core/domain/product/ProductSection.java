package io.dodn.commerce.core.domain.product;

import io.dodn.commerce.core.enums.ProductSectionType;
import io.dodn.commerce.storage.db.core.product.ProductSectionEntity;

public record ProductSection(
        ProductSectionType type,
        String content
) {

    public static ProductSection of(ProductSectionEntity productSectionEntity) {
        return new ProductSection(
                productSectionEntity.getType(),
                productSectionEntity.getContent()
        );
    }
}
