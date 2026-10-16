package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal dto class to update a product
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto {

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 12, message = "El SKU debe tener como maximo 12 caracteres")
    private String sku;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre debe tener como maximo 120 caracteres")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 255, message = "La descripcion debe tener como maximo 255 caracteres")
    private String description;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 120, message = "La marca debe tener como maximo 120 caracteres")
    private String brand;

    @Min(value = 1, message = "El stock minimo debe ser como minimo 1")
    private long minimumStock;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio del producto debe ser positivo")
    @Digits(integer = 10, fraction = 2, message = "El precio no debe exceder 10 digitos enteros y 2 decimales")
    private BigDecimal price;

    @NotNull(message = "El porcentaje de ganancia es obligatorio")
    @Min(value = 0, message = "El porcentaje de ganancia no puede ser menor que 0")
    @Max(value = 100, message = "El porcentaje de ganancia no puede ser mayor que 100")
    private int profitPercentage;

    @NotEmpty(message = "Debes dejarle asignado un proveedor al producto")
    private String provider;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;
}