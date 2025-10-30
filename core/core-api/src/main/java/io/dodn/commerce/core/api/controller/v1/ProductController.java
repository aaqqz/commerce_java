package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.response.ProductDetailResponse;
import io.dodn.commerce.core.api.controller.v1.response.ProductResponse;
import io.dodn.commerce.core.domain.coupon.Coupon;
import io.dodn.commerce.core.domain.coupon.CouponService;
import io.dodn.commerce.core.domain.product.Product;
import io.dodn.commerce.core.domain.product.ProductSection;
import io.dodn.commerce.core.domain.product.ProductSectionService;
import io.dodn.commerce.core.domain.product.ProductService;
import io.dodn.commerce.core.domain.review.RateSummary;
import io.dodn.commerce.core.domain.review.ReviewService;
import io.dodn.commerce.core.domain.review.ReviewTarget;
import io.dodn.commerce.core.enums.ReviewTargetType;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.core.support.response.ApiResponse;
import io.dodn.commerce.core.support.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSectionService productSectionService;
    private final ReviewService reviewService;
    private final CouponService couponService;

    @GetMapping("/v1/products")
    public ApiResponse<PageResponse<ProductResponse>> findProducts(
            @RequestParam Long categoryId,
            @RequestParam Integer offset,
            @RequestParam Integer limit
    ) {
        Page<Product> products = productService.findProducts(categoryId, OffsetLimit.of(offset, limit));

        return ApiResponse.success(new PageResponse<>(ProductResponse.of(products.content()), products.hasNext()));
    }

    @GetMapping("/v1/products/{productId}")
    public ApiResponse<ProductDetailResponse> findProduct(@PathVariable Long productId) {
        Product product = productService.findProduct(productId);
        List<ProductSection> sections = productSectionService.findSections(productId);
        RateSummary rateSummary = reviewService.findRateSummary(ReviewTarget.of(ReviewTargetType.PRODUCT, productId));
        // NOTE: 별도 API 가 나을까?
        List<Coupon> coupons = couponService.getCouponsForProducts(List.of(productId));

        return ApiResponse.success(ProductDetailResponse.of(product, sections, rateSummary, coupons));
    }
}