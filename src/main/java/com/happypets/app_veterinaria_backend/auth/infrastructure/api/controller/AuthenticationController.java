package com.happypets.app_veterinaria_backend.auth.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.auth.application.command.login.LoginUserRequest;
import com.happypets.app_veterinaria_backend.auth.application.command.login.LoginUserResponse;
import com.happypets.app_veterinaria_backend.auth.application.query.AuthVerifyUserRequest;
import com.happypets.app_veterinaria_backend.auth.application.query.AuthVerifyUserResponse;
import com.happypets.app_veterinaria_backend.auth.domain.api.AuthenticationRestController;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.AuthUserDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.LoginRequestDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.mapper.AuthMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.infrastructure.filters.JwtFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Login", description = "Endpoint to login at the API")
    @PostMapping("/login")
    @Override
    public ResponseEntity<Void> loginUser(@RequestBody @Valid  LoginRequestDto loginRequestDto, HttpServletResponse response) {

        LoginUserRequest request = authMapper.mapToLoginRequest(loginRequestDto);
        LoginUserResponse loginUserResponse = mediator.dispatch(request);
        jwtFilter.addSessionCookie(response, loginUserResponse.getToken());
        return null;
    }

    /**
     * Obtain the actual logged user get
     *
     */
    @Operation(summary = "Authentication verification", description = "Endpoint to verify if the user is authenticated")
    @SecurityRequirement(name = "cookieAuth")
    @GetMapping("/me")
    @Override
    public ResponseEntity<AuthUserDto> getCurrentUser() {
        AuthVerifyUserRequest request = new AuthVerifyUserRequest();
        AuthVerifyUserResponse response = mediator.dispatch(request);
        AuthUserDto authUserDto = authMapper.mapToAuthUserDto(response.getAuthUser());
        return ResponseEntity.ok(authUserDto);
    }

    /**
     * Close the active session for the JWT
     *
     */
    @Operation(summary = "Logout", description = "Endpoint to trigger the logout")
    @SecurityRequirement(name = "cookieAuth")
    @PostMapping("/logout")
    @Override
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        jwtFilter.clearSessionCookie(response);
        return ResponseEntity.ok().build();
    }

}
