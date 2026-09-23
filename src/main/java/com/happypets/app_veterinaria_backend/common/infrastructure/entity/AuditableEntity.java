package com.happypets.app_veterinaria_backend.common.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Principal class to define the auditable attributes
 *
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    /**
     * Default attribute
     * Represents the date of the creation entity
     *
     */
    @CreatedDate
    @Column( nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Default attribute
     * Always updated field when the entity has modified
     *
     */
    @LastModifiedDate
    private Instant updatedAt;
}
