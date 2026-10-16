package com.happypets.app_veterinaria_backend.report.application.attendedpets;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord;
import com.happypets.app_veterinaria_backend.report.domain.port.ReportRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAttendedPetsHandler implements RequestHandler<GetAttendedPetsRequest, List<AttendedPetRecord>> {

    private final ReportRepositoryPort reportPort;

    @Override
    public List<AttendedPetRecord> handle(GetAttendedPetsRequest request) {
        return reportPort.findAttendedPets(request.from(), request.to());
    }

    @Override
    public Class<GetAttendedPetsRequest> getRequestType() {
        return GetAttendedPetsRequest.class;
    }
}
