package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.core.domain.coupon.OwnedCouponFinder;
import io.dodn.commerce.core.domain.order.Order;
import io.dodn.commerce.core.domain.order.OrderFinder;
import io.dodn.commerce.core.domain.point.PointAmount;
import io.dodn.commerce.core.domain.point.PointHandler;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.enums.PaymentMethod;
import io.dodn.commerce.core.enums.PointType;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentFinder paymentFinder;
    private final PaymentValidator paymentValidator;
    private final PaymentManager paymentManager;
    private final OrderFinder orderFinder;
    private final OwnedCouponFinder ownedCouponFinder;
    private final PointHandler pointHandler;

    @Transactional
    public Long createPayment(Order order, PaymentDiscount paymentDiscount) {
        paymentValidator.validateAlreadyPaid(order);

        return paymentManager.create(order, paymentDiscount).getId();
    }

    @Transactional
    public Long success(String orderKey, String externalPaymentKey, BigDecimal amount) {
        var order = orderFinder.getByOrderKeyAndState(orderKey, OrderState.CREATED);
        var payment = paymentFinder.getByOrderId(order.getId());

        paymentValidator.validatePaymentSuccess(order, payment, amount);

        /**
         * NOTE: PG 승인 API 호출 => 성공 시 다음 로직으로 진행 | 실패 시 예외 발생
         */

        payment.success(
                externalPaymentKey,
                // NOTE: PG 승인 API 호출의 응답 값 중 `결제 수단` 넣기,
                PaymentMethod.CARD,
                "PG 승인 API 호출의 응답 값 중 `승인번호` 넣기"
        );
        order.paid();

        if (payment.hasAppliedCoupon()) {
            ownedCouponFinder.findOptionalById(payment.getOwnedCouponId())
                    .ifPresent(OwnedCouponEntity::use);
        }

        pointHandler.deduct(new User(payment.getUserId()), PointType.PAYMENT, payment.getId(), payment.getUsedPoint());
        pointHandler.earn(new User(payment.getUserId()), PointType.PAYMENT, payment.getId(), PointAmount.PAYMENT);

        // todo
//        transactionHistoryRepository.save(
//                TransactionHistoryEntity(
//                        type = TransactionType.PAYMENT,
//                        userId = order.userId,
//                        orderId = order.id,
//                        paymentId = payment.id,
//                        externalPaymentKey = externalPaymentKey,
//                        amount = payment.paidAmount,
//                        message = "결제 성공",
//                        occurredAt = payment.paidAt!!,
//            ),
//        )
        return payment.getId();
    }
}
