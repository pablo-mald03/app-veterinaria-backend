package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request;

import com.happypets.app_veterinaria_backend.common.infrastructure.annotations.NotBeforeNow;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal dto class to create a new product
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDto {

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
    private Integer profitPercentage;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 0, message = "El porcentaje de descuento no puede ser menor que 0")
    @Max(value = 100, message = "El porcentaje de descuento no puede ser mayor que 100")
    private Integer discountPercentage;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @NotBeforeNow(message = "La fecha de expiración no puede ser anterior a la fecha y hora actual")
    private LocalDateTime expirationDate;

    @NotEmpty(message = "Debes asignarle un proveedor al producto")
    private String provider;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

}