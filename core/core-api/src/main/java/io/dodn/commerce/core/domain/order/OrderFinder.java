package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemRepository;
import io.dodn.commerce.storage.db.core.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class OrderFinder {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderEntity getByOrderKeyAndState(String orderKey, OrderState state) {
        return orderRepository.findByOrderKeyAndStateAndStatus(orderKey, state, EntityStatus.ACTIVE)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
    }

    public List<OrderItemEntity> getOrderItemByOrderId(Long orderId) {
        List<OrderItemEntity> orderItems = orderItemRepository.findByOrderId(orderId);
        if (orderItems.isEmpty()) throw new  CoreException(ErrorType.NOT_FOUND_DATA);

        return orderItems;
    }

    public List<OrderSummary> getOrders(User user) {
        List<OrderEntity> orders = orderRepository.findByUserIdAndStateAndStatusOrderByIdDesc(user.id(), OrderState.PAID, EntityStatus.ACTIVE);
        if (orders.isEmpty()) return emptyList();

        return orders.stream()
                .map(it -> OrderSummary.of(it, user))
                .toList();
    }
}
