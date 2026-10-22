package com.happypets.app_veterinaria_backend.product.application.command.udpate;


import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Update product request class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest implements Request<UpdateProductResponse>, AuditableRequest {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String brand;
    private long minimumStock;
    private BigDecimal price;
    private int profitPercentage;
    private int discountPercentage;
    private LocalDateTime expirationDate;
    private String provider;
    private Long categoryId;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "PRODUCTOS";
    }

    /**
     * Log action
     *
     */
    @Override
    public String getAction() {
        return "MODIFICACION DE PRODUCTO";
    }

    /**
     * Log detail
     *
     */
    @Override
    public String getDetail(Object response) {

        return "Se modifico el producto con ID: '" + id + "'";
    }
}