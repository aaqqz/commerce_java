package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import io.dodn.commerce.storage.db.core.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFinder {

    private final PaymentRepository paymentRepository;

    public PaymentEntity getByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
    }
}
