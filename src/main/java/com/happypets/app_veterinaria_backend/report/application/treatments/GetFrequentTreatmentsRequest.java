package com.happypets.app_veterinaria_backend.report.application.treatments;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency;

import java.time.LocalDate;
import java.util.List;

public record GetFrequentTreatmentsRequest(LocalDate from, LocalDate to) implements Request<List<TreatmentFrequency>>, AuditableRequest {

    @Override
    public String getModule() {
        return "REPORTES";
    }

    @Override
    public String getAction() {
        return "TRATAMIENTOS FRECUENTES";
    }

    @Override
    public String getDetail(Object response) {
        return "Reporte de tratamientos frecuentes del " + from + " al " + to;
    }
}
