package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.core.domain.order.Order;
import io.dodn.commerce.core.enums.PaymentState;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import io.dodn.commerce.storage.db.core.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentManager {

    private final PaymentRepository paymentRepository;

    public PaymentEntity create(Order order, PaymentDiscount paymentDiscount) {
        PaymentEntity paymentEntity = PaymentEntity.of(
                order.userId(),
                order.id(),
                order.totalPrice(),
                paymentDiscount.useOwnedCouponId(),
                paymentDiscount.couponDiscount(),
                paymentDiscount.usePoint(),
                paymentDiscount.paidAmount(order.totalPrice()),
                PaymentState.READY
        );
        return paymentRepository.save(paymentEntity);
    }
}
