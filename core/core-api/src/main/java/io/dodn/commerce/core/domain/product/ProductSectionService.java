package io.dodn.commerce.core.domain.product;

import io.dodn.commerce.storage.db.core.product.ProductSectionEntity;
import io.dodn.commerce.storage.db.core.product.ProductSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSectionService {

    private final ProductSectionRepository productSectionRepository;

    public List<ProductSection> findSections(Long productId) {
        return productSectionRepository.findByProductId(productId).stream()
                .filter(ProductSectionEntity::isActive)
                .map(ProductSection::of)
                .toList();
    }
}