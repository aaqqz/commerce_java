package io.dodn.commerce.core.domain.qna;

import io.dodn.commerce.core.enums.EntityStatus;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import io.dodn.commerce.storage.db.core.Question.QuestionEntity;
import io.dodn.commerce.storage.db.core.Question.QuestionRepository;
import io.dodn.commerce.storage.db.core.answer.AnswerEntity;
import io.dodn.commerce.storage.db.core.answer.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QnAFinder {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Page<QnA> findQnAByProductId(Long productId, OffsetLimit offsetLimit) {
        Slice<QuestionEntity> questionEntitySlice = questionRepository.findByProductIdAndStatus(productId, EntityStatus.ACTIVE, offsetLimit.toPageable());
        List<Long> questionIds = questionEntitySlice.getContent().stream()
                .map(QuestionEntity::getId)
                .toList();

        List<AnswerEntity> answerEntities = answerRepository.findByQuestionIdInAndStatus(questionIds, EntityStatus.ACTIVE);
        Map<Long, AnswerEntity> answerMap = answerEntities.stream()
                .collect(Collectors.toMap(
                        AnswerEntity::getQuestionId,
                        answer -> answer
                ));

        List<QnA> qnas = questionEntitySlice.getContent().stream()
                .map(questionEntity -> {
                    AnswerEntity answerEntity = answerMap.get(questionEntity.getId());
                    Answer answer = (answerEntity != null) ? Answer.of(answerEntity) : Answer.EMPTY;

                    Question question = Question.of(questionEntity);

                    return new QnA(question, answer);
                })
                .toList();

        return new Page<>(qnas, questionEntitySlice.hasNext());
    }

}
