package com.happypets.app_veterinaria_backend.role.application.command.patch;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Update role role class response
 *
 */
@Data
@AllArgsConstructor
public class PatchRoleResponse {
    private Long roleId;
    private String alias;
}
