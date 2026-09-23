package com.happypets.app_veterinaria_backend.role.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Update permissions role class request
 *
 */
@Data
@AllArgsConstructor
public class UpdateRolePermissionsRequest implements Request<UpdateRolePermissionsResponse> {

    private Long roleId;
    private Set<Long> permissionIds;
}
