package com.happypets.app_veterinaria_backend.role.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Create role class request
 *
 */
@Data
@AllArgsConstructor
public class CreateRoleRequest implements Request<CreateRoleResponse>, AuditableRequest {

    private String alias;
    private String name;
    private String description;
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
        return "CREACION DE ROL";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        return "Se creo un nuevo rol '" + this.alias + "' en el sistema";
    }
}