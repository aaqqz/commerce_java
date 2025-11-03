package io.dodn.commerce.storage.db.core.review;

import io.dodn.commerce.core.enums.ReviewTargetType;
import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(
        name = "review",
        indexes = {
                @Index(name = "udx_user_review", columnList = "userId, reviewKey", unique = true)
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity extends BaseEntity {

    private Long userId;

    private String reviewKey;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private ReviewTargetType targetType;

    private Long targetId;

    private BigDecimal rate;

    @Column(columnDefinition = "TEXT")
    private String content;

    public ReviewEntity(Long userId, String reviewKey, ReviewTargetType targetType, Long targetId, BigDecimal rate, String content) {
        this.userId = userId;
        this.reviewKey = reviewKey;
        this.targetType = targetType;
        this.targetId = targetId;
        this.rate = rate;
        this.content = content;
    }

    protected void editRate(BigDecimal rate) {
        this.rate = rate;
    }

    protected void editContent(String content) {
        this.content = content;
    }

    public void updateContent(BigDecimal rate, String content) {
        this.rate = rate;
        this.content = content;
    }
}
