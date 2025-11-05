package io.dodn.commerce.core.domain.favorite;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.ApplyFavoriteRequestType;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteFinder favoriteFinder;
    private final FavoriteManager favoriteManager;

    public Page<Favorite> findFavorites(User user, OffsetLimit offsetLimit) {
        return favoriteFinder.findByUser(user, offsetLimit);
    }

    public Long applyFavorite(User user, FavoriteContent content) {
        return switch (content.type()) {
            case ApplyFavoriteRequestType.FAVORITE -> addFavorite(user, content.productId());
            case ApplyFavoriteRequestType.UNFAVORITE -> removeFavorite(user, content.productId());
        };
    }

    private Long addFavorite(User user, Long productId) {
        return favoriteManager.addFavorite(user, productId);
    }

    private Long removeFavorite(User user, Long productId) {
        return favoriteManager.removeFavorite(user, productId);
    }
}
