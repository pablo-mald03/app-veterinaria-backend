package com.happypets.app_veterinaria_backend.role.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * disable role class request
 *
 */
@Data
@AllArgsConstructor
public class ChangeStatusRoleRequest implements Request<ChangeStatusRoleResponse>, AuditableRequest {
    private Long roleId;
    private boolean status;

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
        return this.status ? "ACTIVACION DE ROL" : "DESACTIVACION DE ROL";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        String actionText = this.status ? "Se activo el rol ID: " : "Se desactivo el rol ID: ";
        return actionText + this.roleId;
    }
}