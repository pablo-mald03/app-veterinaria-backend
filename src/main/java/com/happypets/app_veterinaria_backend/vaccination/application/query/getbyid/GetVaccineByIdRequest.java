package com.happypets.app_veterinaria_backend.vaccination.application.query.getbyid;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetVaccineByIdRequest implements Request<GetVaccineByIdResponse>, AuditableRequest {

    private Long id;

    @Override
    public String getModule() {
        return "VACUNAS";
    }

    @Override
    public String getAction() {
        return "OBTENER VACUNAS POR ID";
    }

    @Override
    public String getDetail(Object response) {
        return "Se obtuvo la informacion de la vacuna con ID: " + id;
    }
}
