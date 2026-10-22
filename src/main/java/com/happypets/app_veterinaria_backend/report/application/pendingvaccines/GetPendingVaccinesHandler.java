package com.happypets.app_veterinaria_backend.report.application.pendingvaccines;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingTreatmentRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingVaccineRecord;
import com.happypets.app_veterinaria_backend.report.domain.port.ReportRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPendingVaccinesHandler implements RequestHandler<GetPendingVaccinesRequest, List<PendingTreatmentRecord>> {

    private final ReportRepositoryPort reportRepositoryPort;

    @Override
    public List<PendingTreatmentRecord> handle(GetPendingVaccinesRequest request) {
        return reportRepositoryPort.findPendingVaccines(request.until());
    }

    @Override
    public Class<GetPendingVaccinesRequest> getRequestType() {
        return GetPendingVaccinesRequest.class;
    }
}
