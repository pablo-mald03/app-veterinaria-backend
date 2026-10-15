package com.happypets.app_veterinaria_backend.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal user summary domain model
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    private Long id;
    private String userRegistry;
    private String email;
}