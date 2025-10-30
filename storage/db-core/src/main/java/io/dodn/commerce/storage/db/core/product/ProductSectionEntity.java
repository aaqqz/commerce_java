package io.dodn.commerce.storage.db.core.product;

import io.dodn.commerce.core.enums.ProductSectionType;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_section")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSectionEntity extends BaseEntity {

    private Long productId;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private ProductSectionType type;
    private String content;
}
