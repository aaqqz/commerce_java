package io.dodn.commerce.core.domain.point;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.point.PointBalanceEntity;
import io.dodn.commerce.storage.db.core.point.PointBalanceRepository;
import io.dodn.commerce.storage.db.core.point.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointFinder {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public PointBalance findBalance(User user) {
        PointBalanceEntity found = pointBalanceRepository.findByUserId(user.id())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));

        return new PointBalance(found.getUserId(), found.getBalance());
    }

    public List<PointHistory> findHistories(User user) {
        return pointHistoryRepository.findByUserId(user.id()).stream()
                .map(PointHistory::from)
                .toList();
    }
}
