package com.happypets.app_veterinaria_backend.product.application.query.getById;

import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get product by Id response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductByIdResponse {
    private ProductDetail productDetail;
}