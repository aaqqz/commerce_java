package io.dodn.commerce.core.domain.order;

import java.util.List;

public record NewOrder(
        Long userId,
        List<NewOrderItem> items
) {
}
