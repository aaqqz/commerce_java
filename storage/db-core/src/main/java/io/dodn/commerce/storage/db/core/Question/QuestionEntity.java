package io.dodn.commerce.storage.db.core.Question;

import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionEntity extends BaseEntity {

    private Long userId;
    private Long productId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static QuestionEntity register(Long userId, Long productId, String title, String content) {
        QuestionEntity question = new QuestionEntity();
        question.userId = userId;
        question.productId = productId;
        question.title = title;
        question.content = content;

        return question;
    }

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
