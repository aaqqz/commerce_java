package io.dodn.commerce.core.domain.favorite;

import io.dodn.commerce.storage.db.core.favorite.FavoriteEntity;

import java.time.LocalDateTime;

public record Favorite(
        Long id,
        Long userId,
        Long productId,
        LocalDateTime favoritedAt
) {

    public static Favorite of(FavoriteEntity favoriteEntity) {
        return new Favorite(
                favoriteEntity.getId(),
                favoriteEntity.getUserId(),
                favoriteEntity.getProductId(),
                favoriteEntity.getFavoritedDate()
        );
    }
}
