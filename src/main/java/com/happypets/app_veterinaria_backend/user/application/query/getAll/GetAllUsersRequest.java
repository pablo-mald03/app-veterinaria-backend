package com.happypets.app_veterinaria_backend.user.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get all users request
 *
 */
@Data
@AllArgsConstructor
public class GetAllUsersRequest implements Request<GetAllUsersResponse>, AuditableRequest {

    private PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "CONSULTAR USUARIOS";
    }

    @Override
    public String getDetail(Object response) {
        GetAllUsersResponse result = (GetAllUsersResponse) response;
        return "Se obtuvierion: " + result.getAllUsers().getContent().size() + " usuarios registrados";
    }
}
