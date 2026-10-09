package com.happypets.app_veterinaria_backend.vaccination.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineResponse;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteVaccineHandler implements RequestHandler<DeleteVaccineRequest, Void> {

    private VaccineRepository vaccineRepository;

    @Override
    public Void handle(DeleteVaccineRequest request) {

        vaccineRepository.desactiveById(request.getIdVaccine());

        System.out.println("Vaccine with ID: " + request.getIdVaccine() + " desactivated");

        return null;
    }

    @Override
    public Class<DeleteVaccineRequest> getRequestType() {
        return DeleteVaccineRequest.class;
    }
}
