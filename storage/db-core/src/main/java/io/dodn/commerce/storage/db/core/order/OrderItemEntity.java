package io.dodn.commerce.storage.db.core.order;

import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "order_itam")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity extends BaseEntity {
    Long orderId;
    Long productId;
    String productName;
    String thumbnailUrl;
    String shortDescription;
    Long quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
}
