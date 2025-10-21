package io.dodn.commerce.storage.db.core;

import io.dodn.commerce.storage.db.core.config.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "answer")
public class AnswerEntity extends BaseEntity {

    private Long adminId;
    private Long questionId;
    private String content;
}
