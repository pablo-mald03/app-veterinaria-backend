package com.happypets.app_veterinaria_backend.sale.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Principal exception when the sale was not found
 *
 */
public class SaleNotFoundException extends ResourceNotFoundException {
    public SaleNotFoundException(String message) {
        super(message);
    }
}
