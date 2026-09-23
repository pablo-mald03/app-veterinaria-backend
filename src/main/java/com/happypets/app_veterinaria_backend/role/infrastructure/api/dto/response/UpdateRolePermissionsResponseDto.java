package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Principal class dto for update role permissions dto
 *
 */
@Data
@AllArgsConstructor
public class UpdateRolePermissionsResponseDto {
    private Long roleId;
    private String alias;
    private Set<Long> permissionIds;

}
