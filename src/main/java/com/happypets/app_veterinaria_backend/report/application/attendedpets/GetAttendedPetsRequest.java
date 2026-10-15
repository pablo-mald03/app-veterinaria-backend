package com.happypets.app_veterinaria_backend.report.application.attendedpets;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord;

import java.time.LocalDate;
import java.util.List;

public record GetAttendedPetsRequest(LocalDate from, LocalDate to) implements Request<List<AttendedPetRecord>>, AuditableRequest {

    @Override
    public String getModule() {
        return "REPORTES";
    }

    @Override
    public String getAction() {
        return "MASCOTAS ATENDIDAS";
    }

    @Override
    public String getDetail(Object response) {
        return "Reporte de mascotas atendidas del " + from + " al " + to;
    }
}
