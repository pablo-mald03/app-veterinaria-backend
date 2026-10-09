package com.happypets.app_veterinaria_backend.vaccination.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteVaccineRequest implements Request<Void>, AuditableRequest {

    private Long idVaccine;

    @Override
    public String getModule() {
        return "VACUNAS";
    }

    @Override
    public String getAction() {
        return "DESACTIVAR VACUNAS";
    }

    @Override
    public String getDetail(Object response) {
        return "Se ejecuto la accion de desactivar la vacuna con Id: " + idVaccine;
    }
}
