package io.dodn.commerce.storage.db.core.cart;

import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cart_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemEntity extends BaseEntity {

    private Long userId;
    private Long productId;
    private Long quantity;

    public static CartItemEntity create(Long userId, Long productId, Long quantity) {
        CartItemEntity entity = new CartItemEntity();
        entity.userId = userId;
        entity.productId = productId;
        entity.quantity = quantity;

        return entity;
    }

    public void applyQuantity(Long value) {
        this.quantity = value < 1 ? 1 : value;
    }
}