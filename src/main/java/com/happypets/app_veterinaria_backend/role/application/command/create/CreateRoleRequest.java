package com.happypets.app_veterinaria_backend.role.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Create role class request
 *
 */
@Data
@AllArgsConstructor
public class CreateRoleRequest implements Request<CreateRoleResponse> {

    private String alias;
    private String name;
    private String description;
    private Set<Long> permissionIds;
}
