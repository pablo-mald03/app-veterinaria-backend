package com.happypets.app_veterinaria_backend.sale.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

/**
 * Principal exception for stock when its insufficient
 *
 */
public class InsufficientStockException extends ConflictException {
    public InsufficientStockException(String message) {
        super(message);
    }
}