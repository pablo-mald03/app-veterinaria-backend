package com.happypets.app_veterinaria_backend.user.application.command.assignRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * Principal update user roles response class
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesToUserResponse {
    private Long userId;
    private Set<String> roles;
}
