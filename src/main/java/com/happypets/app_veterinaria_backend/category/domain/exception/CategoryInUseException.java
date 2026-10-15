package com.happypets.app_veterinaria_backend.category.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

/**
 * Exception when a category is already in use by any product
 *
 */
public class CategoryInUseException extends ConflictException {
    public CategoryInUseException(String message) {
        super(message);
    }
}
