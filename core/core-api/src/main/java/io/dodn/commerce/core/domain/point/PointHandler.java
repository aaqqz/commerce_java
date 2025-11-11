package io.dodn.commerce.core.domain.point;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.PointType;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.point.PointBalanceEntity;
import io.dodn.commerce.storage.db.core.point.PointBalanceRepository;
import io.dodn.commerce.storage.db.core.point.PointHistoryEntity;
import io.dodn.commerce.storage.db.core.point.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PointHandler {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void earn(User user, PointType type, Long targetId, BigDecimal amount) {
        if (Objects.equals(amount, BigDecimal.ZERO)) return;

        PointBalanceEntity pointBalanceEntity = pointBalanceRepository.findByUserId(user.id())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        pointBalanceEntity.apply(amount);


        pointHistoryRepository.save(
                PointHistoryEntity.of(
                        user.id(),
                        type,
                        targetId,
                        amount,
                        pointBalanceEntity.getBalance()
                )
        );
    }

    @Transactional
    public void deduct(User user, PointType type, Long targetId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) return;

        PointBalanceEntity pointBalanceEntity = pointBalanceRepository.findByUserId(user.id())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));

        pointBalanceEntity.apply(amount.negate());
        pointHistoryRepository.save(
                PointHistoryEntity.of(
                        user.id(),
                        type,
                        targetId,
                        amount,
                        pointBalanceEntity.getBalance()
                )
        );
    }
}
