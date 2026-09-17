package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * This is the principal create role dto
 *
 */
@Data
@AllArgsConstructor
public class CreateRoleRequestDto {

    @NotBlank
    @Size(max = 50, message = "El alias del rol debe tener menos de 50 caracteres")
    private String alias;

    @NotBlank
    @Size(max = 50, message = "El nombre del rol debe tener menos de 50 caracteres")
    private String name;

    @Size(max = 120, message = "La descripcion debe tener menos de 120 caracteres")
    private String description;

    @NotEmpty(message = "Debe seleccionar al menos un permiso")
    private Set<Long> permissionIds;
}
