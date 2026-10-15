package com.happypets.app_veterinaria_backend.product.application.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create product response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResponse {
    private String sku;
}
