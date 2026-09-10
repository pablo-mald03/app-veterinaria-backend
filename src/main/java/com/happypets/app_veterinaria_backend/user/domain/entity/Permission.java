package com.happypets.app_veterinaria_backend.user.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Domain representation for permissions
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Long id;
    private String module;
    private String action;
    private String description;

    /*Auditable properties*/
    private Instant createdAt;
    private Instant updatedAt;
}
