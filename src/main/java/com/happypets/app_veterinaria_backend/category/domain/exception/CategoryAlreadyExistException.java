package com.happypets.app_veterinaria_backend.category.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

public class CategoryAlreadyExistException extends BusinessRuleException {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
