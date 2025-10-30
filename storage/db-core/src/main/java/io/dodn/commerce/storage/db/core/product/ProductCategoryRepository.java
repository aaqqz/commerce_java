package io.dodn.commerce.storage.db.core.product;

import io.dodn.commerce.core.enums.EntityStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    Slice<ProductCategoryEntity> findByCategoryIdAndStatus(Long categoryId, EntityStatus status, Pageable pageable);

    List<ProductCategoryEntity> findByProductIdInAndStatus(List<Long> productIds, EntityStatus status);
}
