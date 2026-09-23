package com.happypets.app_veterinaria_backend.auth.infrastructure.security;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.domain.exceptions.UserNotAuthenticatedException;
import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.infrastructure.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 *
 * Principal class to verify if the user is authenticated
 */
@Service
@RequiredArgsConstructor
public class AuthenticatedUserImpl implements AuthenticatedUserPort {

    //Jwt service
    private final JwtService jwtService;

    @Override
    public AuthUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException("Usuario no autenticado");
        }

        // Get the token from the request
        String token = (String) authentication.getCredentials();

        return AuthUser.builder()
                .id(jwtService.getUserId(token).toString())
                .name(jwtService.getName(token))
                .email(jwtService.getEmail(token))
                .roles(new ArrayList<>(jwtService.getRoles(token)))
                .permissions(new ArrayList<>(jwtService.getPermissions(token)))
                .build();
    }
}
