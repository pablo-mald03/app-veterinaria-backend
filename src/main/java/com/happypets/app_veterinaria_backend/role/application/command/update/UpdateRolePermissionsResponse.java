package com.happypets.app_veterinaria_backend.role.application.command.update;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Update permissions role class response
 *
 */
@Data
@AllArgsConstructor
public class UpdateRolePermissionsResponse {

    private Long roleId;
    private String alias;
    private Set<Long> permissionIds;
}
