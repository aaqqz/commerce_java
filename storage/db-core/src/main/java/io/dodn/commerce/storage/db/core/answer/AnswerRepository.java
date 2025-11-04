package io.dodn.commerce.storage.db.core.answer;

import io.dodn.commerce.core.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    List<AnswerEntity> findByQuestionIdIn(List<Long> questionId);

    List<AnswerEntity> findByQuestionIdInAndStatus(List<Long> questionIds, EntityStatus entityStatus);
}
