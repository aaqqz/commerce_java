package io.dodn.commerce.core.domain.favorite;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.favorite.FavoriteEntity;
import io.dodn.commerce.storage.db.core.favorite.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FavoriteManager {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public Long addFavorite(User user, Long productId) {
        FavoriteEntity favoriteEntity = favoriteRepository
                .findByUserIdAndProductId(user.id(), productId)
                .orElseGet(() -> favoriteRepository.save(
                        FavoriteEntity.create(
                                user.id(),
                                productId
                        )
                ));

        favoriteEntity.favorite();
        return favoriteEntity.getId();
    }

    @Transactional
    public Long removeFavorite(User user, Long productId) {
        FavoriteEntity favoriteEntity = favoriteRepository.findByUserIdAndProductId(user.id(), productId)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));

        favoriteEntity.delete();
        return favoriteEntity.getId();
    }
}
