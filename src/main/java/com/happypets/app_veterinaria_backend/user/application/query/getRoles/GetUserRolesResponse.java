package com.happypets.app_veterinaria_backend.user.application.query.getRoles;

import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get user roles response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRolesResponse {
    private Long userId;
    private String userRegistry;
    private String identification;
    private String email;
    private List<Role> roles;
}