package com.happypets.app_veterinaria_backend.common.infrastructure.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.*;
import com.happypets.app_veterinaria_backend.common.infrastructure.filters.SessionCookieService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Global interceptor handler
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SessionCookieService sessionCookieService;

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
     * Principal handler for conflict when the user is already disabled
     */
    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabled(
            UserDisabledException exception,
            HttpServletResponse response) {
        sessionCookieService.clearSessionCookie(response);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("FORBIDDEN", exception.getMessage(), new HashMap<>()));
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

        String formattedMessage = String.join(", ", errors.values());
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "VALIDATION_ERROR",
                        formattedMessage.isEmpty() ? "Campos invalidos" : formattedMessage,
                        errors
                ));
    }

    /**
     * Principal handler of the auth denied or not authenticated inside control flow
     *
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({JwtException.class, AuthenticationException.class})
    @ResponseBody
    public ErrorResponse unauthorized(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(
                "UNAUTHORIZED",
                "Credenciales invalidas",
                Collections.emptyMap()
        );
    }

    /**
     * Principal handler when user lacks valid authentication
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthenticated(
            UnauthorizedException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        "UNAUTHORIZED",
                        exception.getMessage(),
                        new HashMap<>()
                ));
    }

    /**
     * Principal handler for Database constraints violations
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "DATABASE_CONFLICT",
                        "Ocurrió un conflicto con los datos enviados.",
                        new HashMap<>()
                ));
    }

    /**
     * Fallback handler for ANY unhandled exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "INTERNAL_SERVER_ERROR",
                        "Ocurrió un error inesperado en el servidor. Por favor, intente más tarde.",
                        new HashMap<>()
                ));
    }
}