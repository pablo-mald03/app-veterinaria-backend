package com.happypets.app_veterinaria_backend.role.application.query.rolePermissions;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get permissions by roles request class
 *
 */
@Data
@AllArgsConstructor
public class GetRolePermissionsRequest implements Request<GetRolePermissionsResponse>, AuditableRequest {
    private Long id;

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
        return "PERMISOS POR ROLE";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetRolePermissionsResponse result = (GetRolePermissionsResponse) response;
        return "Se obtuvieron '" + result.getRole().getPermissions().size() + "' permisos del rol: " + result.getRole().getAlias();
    }
}