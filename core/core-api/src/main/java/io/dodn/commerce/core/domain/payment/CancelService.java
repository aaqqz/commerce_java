package io.dodn.commerce.core.domain.payment;

import io.dodn.commerce.core.domain.coupon.OwnedCouponFinder;
import io.dodn.commerce.core.domain.order.OrderFinder;
import io.dodn.commerce.core.domain.order.TransactionHistoryManager;
import io.dodn.commerce.core.domain.point.PointAmount;
import io.dodn.commerce.core.domain.point.PointHandler;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.enums.PointType;
import io.dodn.commerce.storage.db.core.coupon.OwnedCouponEntity;
import io.dodn.commerce.storage.db.core.order.OrderEntity;
import io.dodn.commerce.storage.db.core.payment.CancelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelService {

    private final OrderFinder orderFinder;
    private final PaymentFinder paymentFinder;
    private final PaymentValidator paymentValidator;
    private final OwnedCouponFinder ownedCouponFinder;
    private final PointHandler pointHandler;
    private final CancelManager cancelManager;
    private final TransactionHistoryManager transactionHistoryManager;

    public Long cancel(User user, CancelAction cancelAction) {
        var order = orderFinder.getByOrderKeyAndState(cancelAction.orderKey(), OrderState.PAID);
        var payment = paymentFinder.getByOrderId(order.getId());
        paymentValidator.validatePaymentCancel(user, order, payment);

        order.canceled();

        if (payment.hasAppliedCoupon()) {
            ownedCouponFinder.findOptionalById(payment.getOwnedCouponId())
                    .ifPresent(OwnedCouponEntity::revert);
        }

        pointHandler.earn(new User(payment.getUserId()), PointType.PAYMENT, payment.getId(), payment.getUsedPoint());
        pointHandler.deduct(new User(payment.getUserId()), PointType.PAYMENT, payment.getId(), PointAmount.PAYMENT);

        var cancel = cancelManager.create(payment);
        transactionHistoryManager.createCancel(cancel, payment);

        return cancel.getId();
    }
}
