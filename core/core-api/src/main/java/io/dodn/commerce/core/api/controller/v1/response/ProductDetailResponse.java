package io.dodn.commerce.core.api.controller.v1.response;

import io.dodn.commerce.core.domain.coupon.Coupon;
import io.dodn.commerce.core.domain.product.Product;
import io.dodn.commerce.core.domain.product.ProductSection;
import io.dodn.commerce.core.domain.review.RateSummary;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailResponse(
        String name,
        String thumbnailUrl,
        String description,
        String shortDescription,
        BigDecimal costPrice,
        BigDecimal salesPrice,
        BigDecimal discountedPrice,
        BigDecimal rate,
        Long rateCount,
        List<ProductSectionResponse> sections,
        List<CouponResponse> coupons
) {

    public static ProductDetailResponse of(Product product,
                                 List<ProductSection> sections,
                                 RateSummary rateSummary,
                                 List<Coupon> coupons
    ) {
        return new ProductDetailResponse(
                product.name(),
                product.thumbnailUrl(),
                product.description(),
                product.shortDescription(),
                product.price().costPrice(),
                product.price().salesPrice(),
                product.price().discountedPrice(),
                rateSummary.rate(),
                rateSummary.count(),
                sections.stream()
                        .map(s -> new ProductSectionResponse(s.type(), s.content()))
                        .toList(),
                coupons.stream()
                        .map(CouponResponse::of)
                        .toList()
        );
    }
}
