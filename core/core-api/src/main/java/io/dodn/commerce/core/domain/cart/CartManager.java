package io.dodn.commerce.core.domain.cart;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.cart.CartItemEntity;
import io.dodn.commerce.storage.db.core.cart.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CartManager {

    private final CartItemRepository cartItemRepository;

    @Transactional
    public Long addCartItem(User user, AddCartItem item) {
        CartItemEntity cartItemEntity = cartItemRepository.findByUserIdAndProductId(user.id(), item.productId())
                .map(existing -> {
                    if (existing.isDeleted()) existing.active();

                    existing.applyQuantity(item.quantity());
                    return existing;
                })
                .orElseGet(() -> {
                    CartItemEntity created = CartItemEntity.create(user.id(), item.productId(), item.quantity());
                    return cartItemRepository.save(created);
                });
        return cartItemEntity.getId();
    }

    @Transactional
    public Long updateCartItem(User user, UpdateCartItem item) {
        CartItemEntity found = cartItemRepository.findByIdAndUserIdAndStatus(item.cartItemId(), user.id(), EntityStatus.ACTIVE)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        found.applyQuantity(item.quantity());

        return found.getId();
    }

    @Transactional
    public void deleteCartItem(User user, Long cartItemId) {
        CartItemEntity entity = cartItemRepository.findByIdAndUserIdAndStatus(cartItemId, user.id(), EntityStatus.ACTIVE)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        entity.delete();
    }
}
