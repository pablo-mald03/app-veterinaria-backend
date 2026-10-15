package com.happypets.app_veterinaria_backend.product.domain.entity;

import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal product detail model (with all relationships)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    private Product product;
    private Category category;
}
