package com.happypets.app_veterinaria_backend.sale.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

/**
 * Principal conflict exception for duplicate bill number
 *
 */
public class DuplicateBillNumberException extends ConflictException {
    public DuplicateBillNumberException(String message) {
        super(message);
    }
}