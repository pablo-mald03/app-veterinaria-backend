package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Principal create sale request dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleCreateRequestDto {

    @Size(max = 30, message = "El número de factura no puede exceder los 30 caracteres")
    private String billNumber;

    private LocalDateTime date;

    @DecimalMin(value = "0.00", message = "El porcentaje de IVA no puede ser negativo")
    private BigDecimal iva;

    @NotEmpty(message = "La venta debe contener al menos un detalle de producto")
    @Valid
    private List<SaleItemCreateRequestDto> items;
}