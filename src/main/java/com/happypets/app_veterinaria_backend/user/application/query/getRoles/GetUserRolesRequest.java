package com.happypets.app_veterinaria_backend.user.application.query.getRoles;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get user roles request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRolesRequest implements Request<GetUserRolesResponse>, AuditableRequest {
    private Long userId;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "BUSCAR ROLES DE USUARIO";
    }

    @Override
    public String getDetail(Object response) {
        return "Se buscaron los roles del usuario: " + userId;
    }
}