package io.dodn.commerce.core.api.controller.batch;

import io.dodn.commerce.core.domain.settlement.SettlementService;
import io.dodn.commerce.core.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class SettlementBatchController {

    private final SettlementService settlementService;

    /**
     * NOTE: 정산 대상 적재 배치
     * - 오전 1시 실행
     * - 어제 00:00:00 ~ 23:59:59 데이터 기준으로 정산 데이터 적재
     */
    @PostMapping("/internal-batch/load-targets")
    public ApiResponse<Object> loadTargets(@RequestParam(required = false) LocalDate targetDate) {
        if (targetDate == null) targetDate = LocalDate.now();

        settlementService.loadTargets(
                targetDate,
                targetDate.minusDays(1).atStartOfDay(),
                targetDate.atStartOfDay().minusNanos(1)
        );
        return ApiResponse.success();
    }
}
