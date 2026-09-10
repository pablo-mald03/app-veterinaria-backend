package com.happypets.app_veterinaria_backend.auth.infrastructure.authentication;


import com.happypets.app_veterinaria_backend.auth.domain.authentication.AuthenticationPort;
import com.happypets.app_veterinaria_backend.auth.domain.authentication.AuthenticationResult;
import com.happypets.app_veterinaria_backend.common.infrastructure.service.JwtService;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationPort {

    /**
     * Use the configuration provided information at the config
     *
     */
    private final AuthenticationManager authenticationManager;


    private final JwtService jwtService;

    /**
     * This method generate the authentication process
     *
     */
    @Override
    public AuthenticationResult authenticate(String email, String password) {

        // Validate the credentials at backend
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Extract the entity for the authentication
        UserEntity userEntity = (UserEntity) auth.getPrincipal();

        if (userEntity == null) {
            throw new UserNotFoundException("Usuario no registrado en el sistema");
        }

        //Map the permissions and roles
        Set<String> roles = getRoles(userEntity);

        Set<String> permissions = getPermissions(userEntity);

        // Generate the jwt from the entity
        String token = jwtService.generateToken(
                userEntity,
                userEntity.getId(),
                userEntity.getName(),
                roles,
                permissions
        );

        return AuthenticationResult.builder()
                .token(token)
                .userId(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    /**
     * Principal method to get the user roles
     *
     */
    private Set<String> getRoles(UserEntity userEntity) {

        return userEntity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Principal method to get the permissions
     *
     */
    private Set<String> getPermissions(UserEntity userEntity) {

        return userEntity.getRoles().stream()
                .filter(r -> r.getAssignedPermissions() != null)
                .flatMap(r -> r.getAssignedPermissions().stream())
                .map(p -> p.getModule().toLowerCase() + ":" + p.getAction().toLowerCase())
                .collect(Collectors.toSet());
    }

}
