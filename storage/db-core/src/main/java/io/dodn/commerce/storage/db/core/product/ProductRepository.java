package io.dodn.commerce.storage.db.core.product;

import io.dodn.commerce.core.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByIdInAndStatus(List<Long> ids, EntityStatus status);
}
