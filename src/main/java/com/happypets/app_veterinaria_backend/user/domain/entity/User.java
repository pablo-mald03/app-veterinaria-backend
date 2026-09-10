package com.happypets.app_veterinaria_backend.user.domain.entity;

import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

/**
 * Domain representation for the user
 *
 */
@Data
@Builder
public class User {

    private Long id;
    private String identification;
    private String name;
    private String firstName;
    private String password;
    private String email;
    private String phone;
    private String userRegistry;
    private Set<Role> roles;

    /*Auditable properties*/
    private Instant createdAt;
    private Instant updatedAt;
}
