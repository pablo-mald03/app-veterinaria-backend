package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Assign roles to user response dto
 *
 */
@Data
@Builder
public class AssignRolesToUserResponseDto {
    private Long userId;
    private Set<String> roles;
}
