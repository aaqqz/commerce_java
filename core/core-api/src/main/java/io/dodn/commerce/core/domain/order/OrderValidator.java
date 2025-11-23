package io.dodn.commerce.core.domain.order;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.product.ProductEntity;
import io.dodn.commerce.storage.db.core.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final ProductRepository productRepository;

    public Map<Long, ProductEntity> validateCreate(NewOrder newOrder) {
        Set<Long> orderProductIds = newOrder.items().stream()
                .map(NewOrderItem::productId)
                .collect(Collectors.toSet());

        Map<Long, ProductEntity> productEntityMap = productRepository.findByIdInAndStatus(orderProductIds, EntityStatus.ACTIVE).stream()
                .collect(Collectors.toMap(
                        ProductEntity::getId,
                        product -> product
                ));

        if (productEntityMap.isEmpty())
            throw new CoreException(ErrorType.NOT_FOUND_DATA);

        if (productEntityMap.keySet() != orderProductIds) {
            throw new CoreException(ErrorType.PRODUCT_MISMATCH_IN_ORDER);
        }

        return productEntityMap;
    }

    public void validateSameUser(OrderEntity order, User user) {
        if (!Objects.equals(order.getUserId(), user.id())) throw new CoreException(ErrorType.NOT_FOUND_DATA);
    }
}
