package io.dodn.commerce.core.domain.qna;

import io.dodn.commerce.storage.db.core.Question.QuestionEntity;

public record Question(
        Long id,
        Long userId,
        String title,
        String content
) {

    public static Question of(QuestionEntity questionEntity) {
        return new Question(questionEntity.getId(), questionEntity.getUserId(), questionEntity.getTitle(), questionEntity.getContent());
    }
}
