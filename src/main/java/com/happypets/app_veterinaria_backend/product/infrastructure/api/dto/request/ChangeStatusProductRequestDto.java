package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Change status p[roduct request dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusProductRequestDto {
    @NotNull(message = "El estado es obligatorio")
    private Boolean status;
}