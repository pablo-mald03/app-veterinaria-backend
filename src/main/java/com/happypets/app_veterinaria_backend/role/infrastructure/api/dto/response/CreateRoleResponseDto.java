package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Principal create role response dto
 *
 */
@Data
@AllArgsConstructor
public class CreateRoleResponseDto {
    private Long roleId;
    private String alias;
    private Set<Long> permissionIds;
}