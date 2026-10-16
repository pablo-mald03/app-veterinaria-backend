package com.happypets.app_veterinaria_backend.report.application.pendingvaccines;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingVaccineRecord;

import java.time.LocalDate;
import java.util.List;

/*VACUNA CON PROXIMA DOSIS PARA APLICAR*/
public record GetPendingVaccinesRequest(LocalDate until) implements Request<List<PendingVaccineRecord>>, AuditableRequest {

    @Override
    public String getModule() {
        return "REPORTES";
    }

    @Override
    public String getAction() {
        return "VACUNAS PENDIENTES";
    }

    @Override
    public String getDetail(Object response) {
        return "Reporte de vacunas pendientes hasta " + until;
    }
}
