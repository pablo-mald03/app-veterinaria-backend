package com.happypets.app_veterinaria_backend.report.application.treatments;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency;
import com.happypets.app_veterinaria_backend.report.domain.port.ReportRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFrequentTreatmentsHandler implements RequestHandler<GetFrequentTreatmentsRequest, List<TreatmentFrequency>> {

    private final ReportRepositoryPort reportPort;

    @Override
    public List<TreatmentFrequency> handle(GetFrequentTreatmentsRequest request) {
        return reportPort.findFrequentTreatments(request.from(), request.to());
    }

    @Override
    public Class<GetFrequentTreatmentsRequest> getRequestType() {
        return GetFrequentTreatmentsRequest.class;
    }
}
