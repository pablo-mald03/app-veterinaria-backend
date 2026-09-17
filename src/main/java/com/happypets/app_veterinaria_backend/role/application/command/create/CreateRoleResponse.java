package com.happypets.app_veterinaria_backend.role.application.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Principal clas response when the role was created
 *
 */
@Data
@AllArgsConstructor
public class CreateRoleResponse {

    private Long roleId;
    private String alias;
    private Set<Long> permissionIds;
}
