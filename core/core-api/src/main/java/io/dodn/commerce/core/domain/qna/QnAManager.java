package io.dodn.commerce.core.domain.qna;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import io.dodn.commerce.storage.db.core.Question.QuestionEntity;
import io.dodn.commerce.storage.db.core.Question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QnAManager {

    private final QuestionRepository questionRepository;

    public Long createQuestion(User user, Long productId, QuestionContent content) {
        QuestionEntity saved = questionRepository.save(
                QuestionEntity.register(
                        user.id(),
                        productId,
                        content.title(),
                        content.content()
                )
        );
        return saved.getId();
    }

    @Transactional
    public Long updateQuestion(User user, Long questionId, QuestionContent content) {
        QuestionEntity found = questionRepository.findByIdAndUserId(questionId, user.id())
                .filter(QuestionEntity::isActive)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        found.updateContent(content.title(), content.content());

        return found.getId();
    }

    @Transactional
    public Long deleteQuestion(User user, Long questionId) {
        QuestionEntity found = questionRepository.findByIdAndUserId(questionId, user.id())
                .filter(QuestionEntity::isActive)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_DATA));
        found.delete();

        return found.getId();
    }
}
