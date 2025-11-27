package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.storage.db.core.payment.CancelEntity;
import io.dodn.commerce.storage.db.core.payment.CancelRepository;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelManager {

    private final CancelRepository cancelRepository;

    public CancelEntity create(PaymentEntity payment) {
        CancelEntity cancel = CancelEntity.create(
                payment.getUserId(),
                payment.getOrderId(),
                payment.getId(),
                payment.getOriginAmount(),
                payment.getOwnedCouponId(),
                payment.getCouponDiscount(),
                payment.getUsedPoint(),
                payment.getPaidAmount(),
                payment.getPaidAmount(),
                "PG_API_응답_취소_고유_값_저장"
        );
        return cancelRepository.save(cancel);
    }
}
