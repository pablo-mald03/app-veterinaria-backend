package com.happypets.app_veterinaria_backend.role.application.query.findById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get by id role request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRoleByIdRequest implements Request<GetRoleByIdResponse>, AuditableRequest {
    private Long roleId;

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
        return "BUSCAR ROL POR ID";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetRoleByIdResponse result = (GetRoleByIdResponse) response;
        return "Se consulto el rol con ID: '" + result.getId() + "' en el sistema";
    }
}