package io.dodn.commerce.core.domain;

import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductFinder productFinder;

    public Page<Product> findProducts(Long categoryId, OffsetLimit offsetLimit) {
        return productFinder.findByCategory(categoryId, offsetLimit);
    }
}