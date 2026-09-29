package com.happypets.app_veterinaria_backend.permissions.application.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all permission modules request class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPermissionsRequest implements Request<GetAllPermissionsResponse>, AuditableRequest {
    private String moduleTarget;
    private String actionTarget;
    private PaginationQuery paginationQuery;

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
        return "CONSULTAR PERMISOS";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllPermissionsResponse result = (GetAllPermissionsResponse) response;
        return "Se obtuvieron '" + result.getPermissions().getContent().size() + "' permisos";
    }
}