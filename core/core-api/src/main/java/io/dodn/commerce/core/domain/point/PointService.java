package io.dodn.commerce.core.domain.point;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.point.PointBalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointFinder pointFinder;

    public PointBalance findBalance(User user) {
        return pointFinder.findBalance(user);
    }

    public List<PointHistory> findHistories(User user) {
        return pointFinder.findHistories(user);
    }
}
