package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.request.CreatePaymentRequest;
import io.dodn.commerce.core.api.controller.v1.response.CreatePaymentResponse;
import io.dodn.commerce.core.domain.coupon.OwnedCouponService;
import io.dodn.commerce.core.domain.order.OrderItem;
import io.dodn.commerce.core.domain.order.OrderService;
import io.dodn.commerce.core.domain.payment.PaymentService;
import io.dodn.commerce.core.domain.point.PointService;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.OrderState;
import io.dodn.commerce.core.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final OwnedCouponService ownedCouponService;
    private final PointService pointService;

    @PostMapping("/v1/payments")
    ApiResponse<CreatePaymentResponse> create(User user, @RequestBody CreatePaymentRequest request) {
        var order = orderService.getOrder(user, request.orderKey(), OrderState.CREATED);

        List<Long> productIds = order.items().stream()
                .map(OrderItem::productId)
                .toList();
        var ownedCoupons = ownedCouponService.getOwnedCouponsForCheckout(user, productIds);
        var pointBalance = pointService.findBalance(user);

        var id = paymentService.createPayment(order, request.toPaymentDiscount(ownedCoupons, pointBalance));
        return ApiResponse.success(new CreatePaymentResponse(id));
    }

    @PostMapping("/v1/payments/callback/success")
    public ApiResponse<Object> callbackForSuccess(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam BigDecimal amount) {
        paymentService.success(orderId, paymentKey, amount);
        return ApiResponse.success();
    }

//    @PostMapping("/v1/payments/callback/fail")
//    fun callbackForFail(
//            @RequestParam orderId: String,
//            @RequestParam code: String,
//            @RequestParam message: String,
//            ): ApiResponse<Any> {
//        paymentService.fail(
//                orderKey = orderId,
//                code = code,
//                message = message,
//                )
//        return ApiResponse.success()
//    }

}
