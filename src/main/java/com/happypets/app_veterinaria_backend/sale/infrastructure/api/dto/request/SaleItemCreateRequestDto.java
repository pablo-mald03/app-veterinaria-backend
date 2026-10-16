package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal sale item create request dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemCreateRequestDto {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private long quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio unitario no puede ser negativo")
    private BigDecimal unitPrice;
}