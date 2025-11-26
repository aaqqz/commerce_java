package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.core.domain.order.Order;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.payment.PaymentEntity;
import io.dodn.commerce.storage.db.core.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentValidator {

    private final PaymentRepository paymentRepository;

    public void validateAlreadyPaid(Order order) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(order.id())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        if (paymentEntity.getState().isSuccess()) {
            throw new CoreException(ErrorType.ORDER_ALREADY_PAID);
        }
    }

    public void validatePaymentSuccess(OrderEntity order, PaymentEntity payment, BigDecimal amount) {
        if (!Objects.equals(payment.getUserId(), order.getUserId())) throw new CoreException(ErrorType.NOT_FOUND_DATA);
        if (!payment.getState().isReady()) throw new CoreException(ErrorType.PAYMENT_INVALID_STATE);
        if (!Objects.equals(payment.getPaidAmount(), amount)) throw new CoreException(ErrorType.PAYMENT_AMOUNT_MISMATCH);

    }
}
