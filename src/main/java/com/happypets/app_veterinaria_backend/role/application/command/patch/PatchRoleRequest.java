package com.happypets.app_veterinaria_backend.role.application.command.patch;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Update role class request
 *
 */
@Data
@AllArgsConstructor
public class PatchRoleRequest implements Request<PatchRoleResponse>, AuditableRequest {

    private Long roleId;
    private String alias;
    private String name;
    private String description;

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
        PatchRoleResponse result = (PatchRoleResponse) response;
        return "Se modifico el rol con ID: '" + result.getRoleId() + "' en el sistema";
    }
}