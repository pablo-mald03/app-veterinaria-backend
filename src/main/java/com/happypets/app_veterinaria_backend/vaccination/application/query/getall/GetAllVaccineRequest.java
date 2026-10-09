package com.happypets.app_veterinaria_backend.vaccination.application.query.getall;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllVaccineRequest implements Request<GetAllVaccineResponse>, AuditableRequest {

    PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "VACUNAS";
    }

    @Override
    public String getAction() {
        return "OBTENER VACUNAS";
    }

    @Override
    public String getDetail(Object response) {
        GetAllVaccineResponse result = (GetAllVaccineResponse) response;
        return "Se obtuvieron '" + result.getVaccinePage().getContent().size() + "' vacunas";
    }
}
