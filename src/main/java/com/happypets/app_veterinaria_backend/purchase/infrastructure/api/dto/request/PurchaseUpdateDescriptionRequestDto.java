package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Principal create purchase detail request
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUpdateDescriptionRequestDto {

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no debe exceder 255 caracteres")
    private String description;
}