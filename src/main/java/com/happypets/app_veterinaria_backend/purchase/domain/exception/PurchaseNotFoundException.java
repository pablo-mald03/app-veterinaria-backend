package com.happypets.app_veterinaria_backend.purchase.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Exception when the purchase was not found
 *
 */
public class PurchaseNotFoundException extends ResourceNotFoundException {
    public PurchaseNotFoundException(String message) {
        super(message);
    }
}
