package io.dodn.commerce.storage.db.core.config;

import io.dodn.commerce.core.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private EntityStatus status = EntityStatus.ACTIVE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void active() {
        this.status = EntityStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.status == EntityStatus.ACTIVE;
    }

    public void delete() {
        this.status = EntityStatus.DELETED;
    }

    public boolean isDeleted() {
        return this.status == EntityStatus.DELETED;
    }
}
//INSERT INTO EXAMPLE (
//        name, description, status, created_at, updated_at
//        ) VALUES ('상품 이름_01','상품 설명_01','ACTIVE',NOW(),NOW()),
//        ('상품 이름_02','상품 설명_02','ACTIVE',NOW(),NOW()),
//        ('상품 이름_03','상품 설명_03','ACTIVE',NOW(),NOW()),
//        ('상품 이름_04','상품 설명_04','ACTIVE',NOW(),NOW()),
//        ('상품 이름_05','상품 설명_05','ACTIVE',NOW(),NOW());