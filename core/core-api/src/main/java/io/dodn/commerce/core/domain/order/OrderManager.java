package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemEntity;
import io.dodn.commerce.storage.db.core.order.OrderItemRepository;
import io.dodn.commerce.storage.db.core.order.OrderRepository;
import io.dodn.commerce.storage.db.core.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderManager {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderKeyGenerator orderKeyGenerator;

    public String create(User user, NewOrder newOrder, Map<Long, ProductEntity> productEntityMap) {
        String baseProductName = newOrder.items().stream()
                .findFirst()
                .map(item -> {
                    String base = productEntityMap.get(item.productId()).getName();
                    int size = newOrder.items().size();
                    return size > 1 ? base + " 외 " + (size - 1) + "개" : base;
                })
                .orElse("");

        BigDecimal totalPrice = newOrder.items().stream()
                .map(item ->
                        productEntityMap.get(item.productId())
                                .getDiscountedPrice()
                                .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity orderEntity = OrderEntity.create(
                user.id(),
                orderKeyGenerator.generate(),
                baseProductName,
                totalPrice,
                OrderState.CREATED
        );
        orderRepository.save(orderEntity);

        List<OrderItemEntity> orderItemEntities = newOrder.items().stream()
                .map(item -> {
                    ProductEntity product = productEntityMap.get(item.productId());
                    if (product == null) {
                        throw new CoreException(ErrorType.PRODUCT_MISMATCH_IN_ORDER);
                    }

                    return OrderItemEntity.create(
                            orderEntity.getId(),
                            product.getId(),
                            product.getName(),
                            product.getThumbnailUrl(),
                            product.getShortDescription(),
                            item.quantity(),
                            product.getDiscountedPrice(),
                            product.getDiscountedPrice().multiply(BigDecimal.valueOf(item.quantity()))
                    );
                })
                .toList();
        orderItemRepository.saveAll(orderItemEntities);

        return orderEntity.getOrderKey();
    }
}
