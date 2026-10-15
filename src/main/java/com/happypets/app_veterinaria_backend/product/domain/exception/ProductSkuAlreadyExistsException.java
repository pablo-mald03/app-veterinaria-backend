package com.happypets.app_veterinaria_backend.product.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception when de product sku already exists
 *
 */
public class ProductSkuAlreadyExistsException extends BusinessRuleException {
    public ProductSkuAlreadyExistsException(String message) {
        super(message);
    }
}
