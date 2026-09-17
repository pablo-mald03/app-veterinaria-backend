package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Principal update permissions of any role request dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRolePermissionsRequestDto {

    @NotNull(message = "La lista de permisos no puede ser nula")
    @NotEmpty(message = "Debe enviar al menos un permiso para actualizar")
    private Set<Long> permissionIds;
}
