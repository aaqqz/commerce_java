package io.dodn.commerce.storage.db.core.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSectionRepository extends JpaRepository<ProductSectionEntity, Long> {

    List<ProductSectionEntity> findByProductId(Long productId);
}
