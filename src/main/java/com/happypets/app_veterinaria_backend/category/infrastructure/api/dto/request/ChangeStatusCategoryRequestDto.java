package com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request;

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
public class ChangeStatusCategoryRequestDto {

    @NotNull(message = "El estado es obligatorio")
    private Boolean status;
}
