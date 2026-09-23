package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Disable role response dto
 *
 */
@Data
@AllArgsConstructor
public class ChangeStatusRoleResponseDto {
    private Long roleId;
    private String name;
    private boolean active;
}
