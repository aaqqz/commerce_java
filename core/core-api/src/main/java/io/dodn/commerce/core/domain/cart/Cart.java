package io.dodn.commerce.core.domain.cart;

import io.dodn.commerce.core.domain.order.NewOrder;
import io.dodn.commerce.core.domain.order.NewOrderItem;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;

import java.util.List;
import java.util.Set;

public record Cart(
        Long userId,
        List<CartItem> items
) {
    public NewOrder toNewOrder(Set<Long> targetItemIds) {
        if (items.isEmpty()) throw new CoreException(ErrorType.INVALID_REQUEST);

        List<NewOrderItem> newOrderItems = items.stream()
                .filter(it -> targetItemIds.contains(it.id()))
                .map(it -> new NewOrderItem(it.product().id(), it.quantity()))
                .toList();

        return new NewOrder(userId, newOrderItems);
    }
}
