package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Principal create purchase request
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseCreateRequestDto {

    @Size(max = 255, message = "La descripción no debe exceder 255 caracteres")
    private String description;

    @NotNull(message = "El IVA es obligatorio")
    private BigDecimal iva;

    @NotEmpty(message = "La compra debe incluir al menos un detalle")
    @Valid
    private List<PurchaseDetailCreateRequestDto> details;

}