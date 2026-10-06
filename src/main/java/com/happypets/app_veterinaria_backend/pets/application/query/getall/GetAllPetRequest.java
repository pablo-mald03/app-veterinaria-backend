package com.happypets.app_veterinaria_backend.pets.application.query.getall;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllPetRequest implements Request<GetAllPetResponse>, AuditableRequest {

    PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "MASCOTAS";
    }

    @Override
    public String getAction() {
        return "OBTENER MASCOTAS";
    }

    @Override
    public String getDetail(Object response) {
        GetAllPetResponse result = (GetAllPetResponse) response;
        return "Se obtuvieron '" + result.getPetPage().getContent().size() + "' mascotas";
    }
}
