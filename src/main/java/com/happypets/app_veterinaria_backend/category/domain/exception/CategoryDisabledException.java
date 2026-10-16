package com.happypets.app_veterinaria_backend.category.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception when the entity is disabled
 *
 */
public class CategoryDisabledException extends BusinessRuleException {
    public CategoryDisabledException(String message) {
        super(message);
    }
}
