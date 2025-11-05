package io.dodn.commerce.core.domain.favorite;

import io.dodn.commerce.core.enums.ApplyFavoriteRequestType;

public record FavoriteContent(
        Long productId,
        ApplyFavoriteRequestType type
) {
}
