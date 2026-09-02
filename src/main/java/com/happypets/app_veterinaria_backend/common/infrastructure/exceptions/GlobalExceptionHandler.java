package com.happypets.app_veterinaria_backend.common.infrastructure.exceptions;

import com.happypets.app_veterinaria_backend.common.application.exception.BusinessRuleException;
import com.happypets.app_veterinaria_backend.common.application.exception.ConflictException;
import com.happypets.app_veterinaria_backend.common.application.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global interceptor handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Principal handler for any entity not found or any resource
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "RESOURCE_NOT_FOUND",
                        exception.getMessage(),
                        new HashMap<>()
                ));
    }

    /**
     * Principal handler for any conflict when the services don't response
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "CONFLICT",
                        exception.getMessage(),
                        new HashMap<>()
                ));
    }

    /**
     * Principal handler for any business rule exceptions
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(
            BusinessRuleException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "BUSINESS_RULE_ERROR",
                        exception.getMessage(),
                        new HashMap<>()
                ));
    }

    /**
     * Principal handler when any request isn't allowed
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "VALIDATION_ERROR",
                        "Validation failed",
                        errors
                ));
    }
}
