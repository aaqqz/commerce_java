package io.dodn.commerce.storage.db.core.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SettlementTargetRepository extends JpaRepository<SettlementTargetEntity, Long> {

    @Query(
            """
            SELECT new io.dodn.commerce.storage.db.core.settlement.SettlementTargetSummary(
                settlement.merchantId,
                settlement.settlementDate,
                SUM(settlement.targetAmount),
                COUNT(settlement.id),
                COUNT(DISTINCT settlement.orderId)
            )
            FROM SettlementTargetEntity settlement
                WHERE settlement.settlementDate = :settlementDate
            GROUP BY settlement.merchantId, settlement.settlementDate
            """
    )
    List<SettlementTargetSummary> findBySummary(LocalDate settleDate);
}
