package io.dodn.commerce.core.domain.qna;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.OffsetLimit;
import io.dodn.commerce.core.support.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnAFinder qnaFinder;
    private final QnAManager qnaManager;

    public Page<QnA> findQnA(Long productId, OffsetLimit offsetLimit) {
        return qnaFinder.findQnAByProductId(productId, offsetLimit);
    }

    public Long createQuestion(User user, Long productId, QuestionContent content) {
        return qnaManager.createQuestion(user, productId, content);
    }

    public Long updateQuestion(User user, Long questionId, QuestionContent content) {
        return qnaManager.updateQuestion(user, questionId, content);
    }

    public Long deleteQuestion(User user, Long questionId) {
        return qnaManager.deleteQuestion(user, questionId);
    }

    /**
     * NOTE: 답변은어드민 쪽 기능임
     * fun addAnswer(user: User, questionId: Long, content: String): Long {...}
     * fun updateAnswer(user: User, answerId: Long, content: String): Long {...}
     * fun removeAnswer(user: User, answerId: Long): Long {...}
     */
}
