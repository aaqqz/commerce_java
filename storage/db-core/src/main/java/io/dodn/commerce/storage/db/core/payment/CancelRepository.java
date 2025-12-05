package io.dodn.commerce.storage.db.core.payment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CancelRepository extends JpaRepository<CancelEntity, Long> {

    Slice<CancelEntity> findAllByCanceledAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
