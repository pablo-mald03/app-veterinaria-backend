package com.happypets.app_veterinaria_backend.permissions.application.getCatalog;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get permission catalog request class
 *
 */
@Data
@NoArgsConstructor
public class GetPermissionCatalogRequest implements Request<GetPermissionCatalogResponse>, AuditableRequest {
    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "PERMISOS";
    }

    /**
     * Log action
     */
    @Override
    public String getAction() {
        return "CONSULTAR CATALOGO DE PERMISOS";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetPermissionCatalogResponse result = (GetPermissionCatalogResponse) response;
        return "Se obtuvieron '" + result.getModules().size() + "' modulos y '" + result.getActions().size() + "' acciones";
    }
}