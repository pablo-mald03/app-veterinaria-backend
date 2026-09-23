package com.happypets.app_veterinaria_backend.role.domain.exeptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Principal exception when the role was not found
 *
 */
public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(String role) {
        super("Rol '" + role + "' no encontrado");
    }
}
