package io.dodn.commerce.core.api.controller.v1;

import io.dodn.commerce.core.api.controller.v1.request.ApplyFavoriteRequest;
import io.dodn.commerce.core.api.controller.v1.response.FavoriteResponse;
import io.dodn.commerce.core.domain.favorite.FavoriteService;
import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.response.ApiResponse;
import io.dodn.commerce.core.support.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/v1/favorites")
    public ApiResponse<PageResponse<FavoriteResponse>> findFavorites (
            User user,
            @RequestParam Integer offset,
            @RequestParam Integer limit
    ) {
        var page = favoriteService.findFavorites(user, OffsetLimit.of(offset, limit));
        return ApiResponse.success(new PageResponse<>(FavoriteResponse.of(page.content()), page.hasNext()));
    }

    @PostMapping("/v1/favorites")
    public ApiResponse<Object> applyFavorite(User user, @RequestBody ApplyFavoriteRequest request) {
        favoriteService.applyFavorite(user, request.toContent());
        return ApiResponse.success();
    }
}
