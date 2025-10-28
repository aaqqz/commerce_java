package io.dodn.commerce.core.domain;

import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.storage.db.core.ProductCategoryEntity;
import io.dodn.commerce.storage.db.core.ProductCategoryRepository;
import io.dodn.commerce.storage.db.core.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFinder {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public Page<Product> findByCategory(Long categoryId, OffsetLimit offsetLimit) {
        var productCategoryEntities = productCategoryRepository.findByCategoryIdAndStatus(categoryId, EntityStatus.ACTIVE, offsetLimit.toPageable());
        List<Long> productIds = productCategoryEntities.stream()
                .map(ProductCategoryEntity::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds).stream()
                .map(Product::of)
                .toList();

        return new Page<>(products, productCategoryEntities.hasNext());
    }
}
