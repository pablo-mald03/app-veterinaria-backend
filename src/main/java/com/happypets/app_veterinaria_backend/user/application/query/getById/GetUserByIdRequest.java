package com.happypets.app_veterinaria_backend.user.application.query.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get by id user request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserByIdRequest implements Request<GetUserByIdResponse>, AuditableRequest {
    private Long userId;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "BUSCAR USUARIO POR ID";
    }

    @Override
    public String getDetail(Object response) {
        GetUserByIdResponse result = (GetUserByIdResponse) response;
        return "Se busco la informacion del usuario: " + result.getId();
    }
}
