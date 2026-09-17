package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Principal user detail dto response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponseDto {
    private Long id;
    private String identification;
    private String name;
    private String firstName;
    private String userRegistry;
    private String phone;
    private String email;
    private boolean status;
    private Set<String> roles;
}
