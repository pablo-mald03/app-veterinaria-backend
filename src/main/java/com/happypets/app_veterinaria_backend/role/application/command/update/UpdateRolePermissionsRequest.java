package com.happypets.app_veterinaria_backend.role.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Update permissions role class request
 *
 */
@Data
@AllArgsConstructor
public class UpdateRolePermissionsRequest implements Request<UpdateRolePermissionsResponse>, AuditableRequest {

    private Long roleId;
    private Set<Long> permissionIds;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "ROLES";
    }

    /**
     * Log action
     */
    @Override
    public String getAction() {
        return "MODIFICACION DE ROL";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        UpdateRolePermissionsResponse result = (UpdateRolePermissionsResponse) response;
        return "Se modificaron los permisos del rol: " + result.getAlias();
    }
}