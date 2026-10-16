package com.happypets.app_veterinaria_backend.category.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Principal domain representation entity
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    private Long id;
    private String name;
    private String alias;
    private String description;
    @Builder.Default
    private boolean status = true;
    private Instant createdAt;
    private Instant updatedAt;
}
