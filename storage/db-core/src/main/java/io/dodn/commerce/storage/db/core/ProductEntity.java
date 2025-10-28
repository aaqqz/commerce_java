package io.dodn.commerce.storage.db.core;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseEntity {

    private String name;
    private String thumbnailUrl;
    private String description;
    private String shortDescription;
    private BigDecimal costPrice;
    private BigDecimal salesPrice;
    private BigDecimal discountedPrice;
}
