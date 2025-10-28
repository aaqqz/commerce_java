package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.response.ProductResponse;
import io.dodn.commerce.core.domain.Product;
import io.dodn.commerce.core.domain.ProductService;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.core.support.response.ApiResponse;
import io.dodn.commerce.core.support.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/v1/products")
    public ApiResponse<PageResponse<ProductResponse>> findProducts(
            @RequestParam Long categoryId,
            @RequestParam Integer offset,
            @RequestParam Integer limit
    ) {
        Page<Product> products = productService.findProducts(categoryId, OffsetLimit.of(offset, limit));

        return ApiResponse.success(new PageResponse<>(ProductResponse.of(products.content()), products.hasNext()));
    }
}