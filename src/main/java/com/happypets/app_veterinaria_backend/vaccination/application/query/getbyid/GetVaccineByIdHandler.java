package com.happypets.app_veterinaria_backend.vaccination.application.query.getbyid;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.vaccination.domain.exception.VaccineNotFoundException;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetVaccineByIdHandler implements RequestHandler<GetVaccineByIdRequest, GetVaccineByIdResponse> {

    private final VaccineRepository vaccineRepository;

    @Override
    public GetVaccineByIdResponse handle(GetVaccineByIdRequest request) {
        log.info("Getting vaccine with id {}", request.getId());

        Vaccine vaccine = vaccineRepository.findById(request.getId()).orElseThrow(() -> new VaccineNotFoundException(request.getId()));

        log.info("Found product with id {}", vaccine.getIdVaccine());
        return new GetVaccineByIdResponse(vaccine);
    }

    @Override
    public Class<GetVaccineByIdRequest> getRequestType() {
        return GetVaccineByIdRequest.class;
    }
}
