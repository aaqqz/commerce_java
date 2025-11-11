package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.response.PointResponse;
import io.dodn.commerce.core.domain.point.PointBalance;
import io.dodn.commerce.core.domain.point.PointHistory;
import io.dodn.commerce.core.domain.point.PointService;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/v1/points")
    public ApiResponse<PointResponse> getPoint(User user) {
        PointBalance balance = pointService.findBalance(user);
        List<PointHistory> histories = pointService.findHistories(user);

        return ApiResponse.success(PointResponse.of(balance, histories));
    }

}

