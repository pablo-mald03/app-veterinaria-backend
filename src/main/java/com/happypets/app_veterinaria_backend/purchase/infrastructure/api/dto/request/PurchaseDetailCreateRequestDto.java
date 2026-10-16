package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal create purchase detail request
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailCreateRequestDto {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull(message = "El porcentaje de ganancia es obligatorio")
    @Min(value = 0, message = "El porcentaje de ganancia no puede ser menor que 0")
    @Max(value = 100, message = "El porcentaje de ganancia no puede ser mayor que 100")
    private int profitPercentage;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private long quantity;

    @NotNull(message = "El precio unitario de compra es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor a cero")
    private BigDecimal unitPrice;
}