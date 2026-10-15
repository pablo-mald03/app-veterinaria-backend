package com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Update category request dto
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    private String name;
    @NotBlank(message = "La descripcion de la categoria es obligatoria")
    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    private String description;
}