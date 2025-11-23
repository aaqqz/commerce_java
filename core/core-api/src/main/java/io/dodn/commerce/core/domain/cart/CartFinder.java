package io.dodn.commerce.core.domain.cart;

import io.dodn.commerce.core.domain.product.Product;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.storage.db.core.cart.CartItemEntity;
import io.dodn.commerce.storage.db.core.cart.CartItemRepository;
import io.dodn.commerce.storage.db.core.product.ProductEntity;
import io.dodn.commerce.storage.db.core.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartFinder {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public Cart getCart(User user) {
        List<CartItemEntity> cartItemEntities = cartItemRepository.findByUserIdAndStatus(user.id(), EntityStatus.ACTIVE);

        List<Long> productIds = cartItemEntities.stream()
                .map(CartItemEntity::getProductId)
                .toList();

        Map<Long, ProductEntity> productEntityMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(
                        ProductEntity::getId,
                        product -> product
                ));

        List<CartItem> cartItems = cartItemEntities.stream()
                .map(cartItemEntity -> new CartItem(
                        cartItemEntity.getId(),
                        Product.of(productEntityMap.get(cartItemEntity.getProductId())),
                        cartItemEntity.getQuantity()
                ))
                .toList();
        return new Cart(user.id(), cartItems);
    }
}
