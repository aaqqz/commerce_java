package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.storage.db.core.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFinder orderFinder;
    private final OrderValidator orderValidator;
    private final OrderManager orderManager;

    @Transactional
    public String create(User user, NewOrder newOrder) {
        Map<Long, ProductEntity> productEntityMap = orderValidator.validateCreate(newOrder);
        return orderManager.create(user, newOrder, productEntityMap);
    }

    @Transactional
    public Order getOrder(User user, String orderKey, OrderState state) {
        var order = orderFinder.getByOrderKeyAndState(orderKey, state);
        orderValidator.validateSameUser(order, user);

        var items = orderFinder.getOrderItemByOrderId(order.getId());

        return Order.of(order, user, items);
    }

    public List<OrderSummary> getOrders(User user) {
        return orderFinder.getOrders(user);
    }
}
