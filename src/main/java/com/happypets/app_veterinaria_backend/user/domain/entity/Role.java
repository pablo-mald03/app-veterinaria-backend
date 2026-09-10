package com.happypets.app_veterinaria_backend.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

/**
 * Domain representation for role
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private Long id;
    private String alias;
    private String name;
    private String description;
    private Set<Permission> permissions;

    /*Auditable properties*/
    private Instant createdAt;
    private Instant updatedAt;
}
