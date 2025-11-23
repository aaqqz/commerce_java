package io.dodn.commerce.core.api.controller.v1.request;

import io.dodn.commerce.core.domain.order.NewOrder;
import io.dodn.commerce.core.domain.order.NewOrderItem;
import io.dodn.commerce.core.domain.user.User;

import java.util.List;

public record CreateOrderRequest(
        Long productId,
        Long quantity
) {
    public NewOrder toNewOrder(User user) {
        return new NewOrder(user.id(), List.of(new NewOrderItem(productId, quantity)));
    }
}
