package com.happypets.app_veterinaria_backend.vaccination.application.query.getall;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllVaccineHandler implements RequestHandler<GetAllVaccineRequest, GetAllVaccineResponse> {

    private final VaccineRepository vaccineRepository;

    @Override
    public GetAllVaccineResponse handle(GetAllVaccineRequest request) {
        log.info("Getting all pets");

        PaginationResult<Vaccine> vaccines = vaccineRepository.findAll(request.getPaginationQuery());

        return new GetAllVaccineResponse(vaccines);
    }

    @Override
    public Class<GetAllVaccineRequest> getRequestType() {
        return GetAllVaccineRequest.class;
    }
}
