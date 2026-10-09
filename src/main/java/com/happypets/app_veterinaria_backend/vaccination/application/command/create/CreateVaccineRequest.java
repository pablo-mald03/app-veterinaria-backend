package com.happypets.app_veterinaria_backend.vaccination.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.Data;

@Data
public class CreateVaccineRequest implements Request<CreateVaccineResponse>, AuditableRequest {

    private String name;
    private String description;
    private String species;
    private Integer dosesRequired;
    private Integer intervalDays;
    private boolean status;

    @Override
    public String getModule() {
        return "VACUNAS";
    }

    @Override
    public String getAction() {
        return "CREAR VACUNA";
    }

    @Override
    public String getDetail(Object response) {
        CreateVaccineResponse result = (CreateVaccineResponse) response;
        return "Se registro la vacuna con ID: " + result.getVaccine().getIdVaccine();
    }
}
