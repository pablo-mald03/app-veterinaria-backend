package com.happypets.app_veterinaria_backend.auth.infrastructure.security;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.domain.exceptions.UserNotAuthenticatedException;
import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.domain.exception.UserDisabledException;
import com.happypets.app_veterinaria_backend.common.infrastructure.service.JwtService;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * Principal class to verify if the user is authenticated
 */
@Service
@RequiredArgsConstructor
public class AuthenticatedUserImpl implements AuthenticatedUserPort {

    //Jwt service
    private final JwtService jwtService;
    private final UserRepositoryPort userRepositoryPort;

    /**
     * Method to get the authenticated user
     *
     */
    @Override
    public AuthUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException("Usuario no autenticado en el sistema");
        }

        String token = (String) authentication.getCredentials();
        Long userId = jwtService.getUserId(token);

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!user.isStatus()) {
            throw new UserDisabledException("El usuario se encuentra deshabilitado en el sistema");
        }

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .toList();

        List<String> permissions = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                .filter(a -> !a.startsWith("ROLE_"))
                .toList();

        return AuthUser.builder()
                .id(userId.toString())
                .name(jwtService.getName(token))
                .email(jwtService.getEmail(token))
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    /**
     * Method to get the user id
     *
     */
    @Override
    public Long getUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException("Usuario no autenticado en el sistema");
        }

        String token = (String) authentication.getCredentials();

        return jwtService.getUserId(token);
    }
}
