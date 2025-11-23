package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.request.CreateOrderFromCartRequest;
import io.dodn.commerce.core.api.controller.v1.request.CreateOrderRequest;
import io.dodn.commerce.core.api.controller.v1.response.CreateOrderResponse;
import io.dodn.commerce.core.api.controller.v1.response.OrderCheckoutResponse;
import io.dodn.commerce.core.api.controller.v1.response.OrderListResponse;
import io.dodn.commerce.core.api.controller.v1.response.OrderResponse;
import io.dodn.commerce.core.domain.cart.Cart;
import io.dodn.commerce.core.domain.cart.CartService;
import io.dodn.commerce.core.domain.coupon.OwnedCouponService;
import io.dodn.commerce.core.domain.order.OrderItem;
import io.dodn.commerce.core.domain.order.OrderService;
import io.dodn.commerce.core.domain.point.PointService;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final OwnedCouponService ownedCouponService;
    private final PointService pointService;

    @PostMapping("/v1/orders")
    public ApiResponse<CreateOrderResponse> create(User user, @RequestBody CreateOrderRequest request) {
        var orderKey = orderService.create(user, request.toNewOrder(user));

        return ApiResponse.success(new CreateOrderResponse(orderKey));
    }

    @PostMapping("/v1/cart-orders")
    public ApiResponse<CreateOrderResponse> createFromCart(User user, @RequestBody CreateOrderFromCartRequest request) {
        Cart cart = cartService.getCart(user);
        String orderKey = orderService.create(user, cart.toNewOrder(request.cartItemIds()));

        return ApiResponse.success(new CreateOrderResponse(orderKey));
    }

    @GetMapping("/v1/orders/{orderKey}/checkout")
    public ApiResponse<OrderCheckoutResponse> findOrderForCheckout(User user, @PathVariable String orderKey) {
        var order = orderService.getOrder(user, orderKey, OrderState.CREATED);

        List<Long> productIds = order.items().stream()
                .map(OrderItem::productId)
                .toList();
        var ownedCoupons = ownedCouponService.getOwnedCouponsForCheckout(user, productIds);
        var pointBalance = pointService.findBalance(user);
        return ApiResponse.success(OrderCheckoutResponse.of(order, ownedCoupons, pointBalance));
    }

    @GetMapping("/v1/orders")
    public ApiResponse<List<OrderListResponse>> getOrders(User user) {
        var orderSummaries = orderService.getOrders(user);
        return ApiResponse.success(OrderListResponse.of(orderSummaries));
    }

    @GetMapping("/v1/orders/{orderKey}")
    public ApiResponse<OrderResponse> getOrder(User user, @PathVariable String orderKey) {
        var order = orderService.getOrder(user, orderKey, OrderState.PAID);
        return ApiResponse.success(OrderResponse.of(order));
    }
}
