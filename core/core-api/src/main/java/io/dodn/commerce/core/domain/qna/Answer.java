package io.dodn.commerce.core.domain.qna;

import io.dodn.commerce.storage.db.core.answer.AnswerEntity;

public record Answer(
        Long id,
        Long adminId,
        String content
) {
    public static final Answer EMPTY = new Answer(-1L, -1L, "");

    public static Answer of(AnswerEntity answerEntity) {
        return new Answer(answerEntity.getId(), answerEntity.getAdminId(), answerEntity.getContent());
    }
}