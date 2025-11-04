package io.dodn.commerce.storage.db.core.answer;

import io.dodn.commerce.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerEntity extends BaseEntity {
    Long adminId;
    Long questionId;
    String content;
}
