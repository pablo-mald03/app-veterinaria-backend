package com.happypets.app_veterinaria_backend.product.application.command.udpate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update product response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductResponse {
    private String sku;
}
