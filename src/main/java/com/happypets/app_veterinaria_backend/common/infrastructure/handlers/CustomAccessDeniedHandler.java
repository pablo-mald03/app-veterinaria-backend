package com.happypets.app_veterinaria_backend.common.infrastructure.handlers;

import com.happypets.app_veterinaria_backend.common.infrastructure.exceptions.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;

/**
 * Principal class handler when the access was denied
 *
 */
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    //Attribute
    private final ObjectMapper objectMapper;


    /**
     * Method to handle the request
     *
     */
    @Override
    public void handle(@NonNull HttpServletRequest request,
                       @NonNull HttpServletResponse response,
                       @NonNull AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(
                "FORBIDDEN",
                "No tienes permisos para realizar esta accion",
                Collections.emptyMap()
        );

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
