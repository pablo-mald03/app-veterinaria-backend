package com.happypets.app_veterinaria_backend.auth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal user domain auth representation for requests who need to check the user if is authenticated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {

    private String id;
    private String name;
    private String email;
    private List<String> roles;
    private List<String> permissions;
}
