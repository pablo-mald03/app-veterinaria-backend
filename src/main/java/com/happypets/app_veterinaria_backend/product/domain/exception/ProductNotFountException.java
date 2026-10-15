package com.happypets.app_veterinaria_backend.product.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Exception when the product doesn't exist
 *
 */
public class ProductNotFountException extends ResourceNotFoundException {
    public ProductNotFountException(String message) {
        super(message);
    }
}
