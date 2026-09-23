package com.happypets.app_veterinaria_backend.user.application.query.getById;

import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Principal get by id user response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByIdResponse {
    private Long id;
    private String identification;
    private String name;
    private String firstName;
    private String phone;
    private String userRegistry;
    private String email;
    private boolean status;
    private Set<Role> roles;
}
