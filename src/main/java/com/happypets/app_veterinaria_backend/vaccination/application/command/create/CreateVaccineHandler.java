package com.happypets.app_veterinaria_backend.vaccination.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetResponse;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateVaccineHandler implements RequestHandler<CreateVaccineRequest, CreateVaccineResponse> {

    private VaccineRepository vaccineRepository;

    @Override
    public CreateVaccineResponse handle(CreateVaccineRequest request) {

        log.info("Create Vaccine Request: " + request);

        Vaccine vaccine = Vaccine.builder().name(request.getName()).
                description(request.getDescription()).
                species(request.getSpecies()).
                dosesRequired(request.getDosesRequired()).
                intervalDays(request.getIntervalDays()).
                status(request.isStatus()).
                build();

        Vaccine createVaccine = vaccineRepository.save(vaccine);
        log.info("Created Vaccine: " + createVaccine);

        return new CreateVaccineResponse(createVaccine);
    }

    @Override
    public Class<CreateVaccineRequest> getRequestType() {
        return CreateVaccineRequest.class;
    }
}
