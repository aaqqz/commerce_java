package io.dodn.commerce.storage.db.core.order;

import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.enums.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("""
            SELECT item FROM OrderItemEntity item
                JOIN OrderEntity orderEntity ON item.orderId = orderEntity.id
            WHERE orderEntity.userId = :userId
                AND orderEntity.state = :state
                AND orderEntity.status = :status
                AND orderEntity.createdAt >= :fromDate
                AND item.productId = :productId
                AND item.status = :status
            """)
    List<OrderItemEntity> findRecentOrderItemsForProduct(
            @Param("userId") Long userId,
            @Param("productId") Long productId,
            @Param("state") OrderState state,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("status") EntityStatus status
    );

    List<OrderItemEntity> findByOrderId(Long orderId);
}
