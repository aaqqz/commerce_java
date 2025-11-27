package io.dodn.commerce.storage.db.core.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelRepository extends JpaRepository<CancelEntity, Long> {
}
