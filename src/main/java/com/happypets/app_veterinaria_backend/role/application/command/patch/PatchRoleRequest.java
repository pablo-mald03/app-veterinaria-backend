package com.happypets.app_veterinaria_backend.role.application.command.patch;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Update role class request
 *
 */
@Data
@AllArgsConstructor
public class PatchRoleRequest implements Request<PatchRoleResponse> {

    private Long roleId;
    private String alias;
    private String name;
    private String description;
}
