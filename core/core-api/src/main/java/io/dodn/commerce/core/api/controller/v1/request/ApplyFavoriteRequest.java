package io.dodn.commerce.core.api.controller.v1.request;

import io.dodn.commerce.core.domain.favorite.FavoriteContent;
import io.dodn.commerce.core.enums.ApplyFavoriteRequestType;

public record ApplyFavoriteRequest(
        Long productId,
        ApplyFavoriteRequestType type
) {
    public FavoriteContent toContent() {
        return new FavoriteContent(productId, type);
    }
}
