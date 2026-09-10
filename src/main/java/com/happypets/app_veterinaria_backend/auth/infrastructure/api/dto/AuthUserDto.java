package com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal user dto for requests who need to check the user if is authenticated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {

    private String id;
    private String name;
    private String email;
    private List<String> roles;
    private List<String> permissions;
}
