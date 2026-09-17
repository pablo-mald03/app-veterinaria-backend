package com.happypets.app_veterinaria_backend.role.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * disable role class request
 *
 */
@Data
@AllArgsConstructor
public class ChangeStatusRoleRequest implements Request<ChangeStatusRoleResponse> {
    private Long roleId;
    private boolean status;
}
