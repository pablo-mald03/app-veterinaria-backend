package com.happypets.app_veterinaria_backend.common.infrastructure.handlers;

import com.happypets.app_veterinaria_backend.common.infrastructure.exceptions.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;

/**
 * Principal handler for authentication (WHEN ITS UNAUTHORIZED)
 *
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //Attribute
    private final ObjectMapper objectMapper;

    /**
     * Method to handle the request
     *
     */
    @Override
    public void commence(@NonNull HttpServletRequest request,
                         @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(
                "UNAUTHORIZED",
                "No autenticado o token invalido/expirado",
                Collections.emptyMap()
        );

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
