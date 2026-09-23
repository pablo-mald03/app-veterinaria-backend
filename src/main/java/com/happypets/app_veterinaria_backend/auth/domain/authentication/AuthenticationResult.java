package com.happypets.app_veterinaria_backend.auth.domain.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Principal authentication result domain representation
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResult {
    private String token;
    private Long userId;
    private String name;
    private String email;
    private Set<String> roles;
    private Set<String> permissions;
}