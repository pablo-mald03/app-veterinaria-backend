package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Expiring product query dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExpiringProductsQueryDto {

    @Schema(description = "Días hacia adelante en los que se buscan productos por vencer", defaultValue = "5", example = "5")
    @Min(value = 1, message = "Los días deben ser como mínimo 1")
    @Max(value = 365, message = "Los días no pueden ser mayores a 365")
    private int days = 5;
}