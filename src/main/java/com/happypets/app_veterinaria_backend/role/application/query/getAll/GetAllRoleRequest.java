package com.happypets.app_veterinaria_backend.role.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get all role request
 *
 */
@Data
@AllArgsConstructor
public class GetAllRoleRequest implements Request<GetAllRoleResponse>, AuditableRequest {

    private PaginationQuery paginationQuery;

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
        return "CONSULTAR ROLES";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllRoleResponse result = (GetAllRoleResponse) response;
        return "Se obtuvieron '" + result.getRoles().getContent().size() + "' roles";
    }
}