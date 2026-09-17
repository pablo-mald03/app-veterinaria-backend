package com.happypets.app_veterinaria_backend.role.application.query.rolePermissions;

import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get permissions by roles response class
 *
 */
@Data
@AllArgsConstructor
public class GetRolePermissionsResponse {

    private Role role;
}
