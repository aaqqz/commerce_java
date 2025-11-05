package io.dodn.commerce.storage.db.core.favorite;

import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "favorite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteEntity extends BaseEntity {

    private Long userId;
    private Long productId;
    private LocalDateTime favoritedDate;

    public static FavoriteEntity create(Long userId, Long productId) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.userId = userId;
        favoriteEntity.productId = productId;
        favoriteEntity.favoritedDate = LocalDateTime.now();

        return favoriteEntity;
    }

    public void favorite() {
        active();
        favoritedDate = LocalDateTime.now();
    }
}
