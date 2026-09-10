package com.happypets.app_veterinaria_backend.auth.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.auth.domain.api.AuthenticationRestController;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.AuthUserDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.LoginRequestDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.RecoverPasswordRequestDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.mapper.AuthMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.infrastructure.filters.JwtFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Principal controller for authentication module
 *
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication module for the application")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationRestController {

    private final Mediator mediator;
    private final AuthMapper authMapper;

    /*Principal web dependency filter*/
    private final JwtFilter jwtFilter;

    /**
     * Login endpoint request
     *
     */
    @PostMapping("/login")
    @Override
    public ResponseEntity<Void> loginUser(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return null;
    }

    /**
     * Obtain the actual logged user get
     *
     */
    @GetMapping("/me")
    @Override
    public ResponseEntity<AuthUserDto> getCurrentUser() {
        return null;
    }

    /**
     * Close the active session for the JWT
     *
     */
    @PostMapping("/logout")
    @Override
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        jwtFilter.clearSessionCookie(response);
        return ResponseEntity.ok().build();
    }

    /**
     * Recover password endpoint request
     *
     */
    @PostMapping("/recover-password")
    @Override
    public ResponseEntity<Void> recoverPassword(RecoverPasswordRequestDto requestDto) {
        return null;
    }
}
