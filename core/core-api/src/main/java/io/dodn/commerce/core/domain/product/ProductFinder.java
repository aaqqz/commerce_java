package io.dodn.commerce.core.domain.product;

import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.product.ProductCategoryEntity;
import io.dodn.commerce.storage.db.core.product.ProductCategoryRepository;
import io.dodn.commerce.storage.db.core.product.ProductEntity;
import io.dodn.commerce.storage.db.core.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFinder {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public Page<Product> findByCategory(Long categoryId, OffsetLimit offsetLimit) {
        Slice<ProductCategoryEntity> productCategoryEntitySlice = productCategoryRepository.findByCategoryIdAndStatus(categoryId, EntityStatus.ACTIVE, offsetLimit.toPageable());
        List<Long> productIds = productCategoryEntitySlice.getContent().stream()
                .map(ProductCategoryEntity::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds).stream()
                .map(Product::of)
                .toList();

        return new Page<>(products, productCategoryEntitySlice.hasNext());
    }

    public Product find(Long productId) {
        return productRepository.findById(productId)
                .filter(ProductEntity::isActive)
                .map(Product::of)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
    }
}
