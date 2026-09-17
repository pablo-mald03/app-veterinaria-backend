package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal change status request dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusRoleRequestDto {

    @NotNull(message = "El estado es obligatorio")
    private Boolean status;
}
