package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.request.AddCartItemRequest;
import io.dodn.commerce.core.api.controller.v1.request.UpdateCartItemRequest;
import io.dodn.commerce.core.api.controller.v1.response.CartResponse;
import io.dodn.commerce.core.domain.cart.Cart;
import io.dodn.commerce.core.domain.cart.CartService;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/v1/cart")
    public ApiResponse<CartResponse> getCart(User user) {
        Cart cart = cartService.getCart(user);
        return ApiResponse.success(CartResponse.of(cart));
    }

    @PostMapping("/v1/cart/items")
    public ApiResponse<Object> addCartItem(User user, @RequestBody AddCartItemRequest request) {
        cartService.addCartItem(user, request.toAddCartItem());
        return ApiResponse.success();
    }

    @PutMapping("/v1/cart/items/{cartItemId}")
    public ApiResponse<Object> updateCartItem(User user, @PathVariable Long cartItemId, @RequestBody UpdateCartItemRequest request) {
        cartService.updateCartItem(user, request.toUpdateCartItem(cartItemId));
        return ApiResponse.success();
    }

    @DeleteMapping("/v1/cart/items/{cartItemId}")
    public ApiResponse<Object> deleteCartItem(User user, @PathVariable Long cartItemId) {
        cartService.deleteCartItem(user, cartItemId);
        return ApiResponse.success();
    }
}
