package com.happypets.app_veterinaria_backend.common.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Principal class used to map the error response at the handlers
 *
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private Map<String, String> errors;

}
