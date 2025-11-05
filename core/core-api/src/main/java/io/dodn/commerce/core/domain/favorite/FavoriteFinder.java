package io.dodn.commerce.core.domain.favorite;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.storage.db.core.favorite.FavoriteEntity;
import io.dodn.commerce.storage.db.core.favorite.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteFinder {

    private final FavoriteRepository favoriteRepository;

    public Page<Favorite> findByUser(User user, OffsetLimit offsetLimit) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);

        Slice<FavoriteEntity> favoriteEntitySlice = favoriteRepository.findByUserIdAndStatusAndUpdatedAtAfter(
                user.id(),
                EntityStatus.ACTIVE,
                cutoff,
                offsetLimit.toPageable()
        );

        List<Favorite> favorites = favoriteEntitySlice.getContent().stream()
                .map(Favorite::of)
                .toList();

        return new Page<>(favorites, favoriteEntitySlice.hasNext());
    }
}
