package io.dodn.commerce.storage.db.core.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Long> {
}
