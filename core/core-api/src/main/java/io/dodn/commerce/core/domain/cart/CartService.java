package io.dodn.commerce.core.domain.cart;

import io.dodn.commerce.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartFinder cartFinder;
    private final CartManager cartManager;

    public List<CartItem> getCart(User user) {
        return cartFinder.getCart(user);
    }

    public Long addCartItem(User user, AddCartItem item) {
        return cartManager.addCartItem(user, item);
    }

    public Long updateCartItem(User user, UpdateCartItem item) {
        return cartManager.updateCartItem(user, item);
    }

    public void deleteCartItem(User user, Long cartItemId) {
        cartManager.deleteCartItem(user, cartItemId);
    }
}
