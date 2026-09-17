package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal update role request dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchRoleRequestDto {

    @NotBlank
    @Size(max = 50, message = "El alias del rol debe tener menos de 50 caracteres")
    private String alias;

    @NotBlank
    @Size(max = 50, message = "El nombre del rol debe tener menos de 50 caracteres")
    private String name;

    @Size(max = 120, message = "La descripcion debe tener menos de 120 caracteres")
    private String description;
}
