package com.happypets.app_veterinaria_backend.pets.application.query.getbyid;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.pets.domain.exception.PetNotFoundException;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetPetByIdHandler implements RequestHandler<GetPetByIdRequest, GetPetByIdResponse> {

    private final PetRepository petRepository;

    @Override
    public GetPetByIdResponse handle(GetPetByIdRequest request) {

        log.info("Getting pet with id {}", request.getId());

        Pet pet = petRepository.findById(request.getId()).orElseThrow(() -> new PetNotFoundException(request.getId()));

        log.info("Found product with id {}", pet.getIdPet());
        return new GetPetByIdResponse(pet);
    }


    @Override
    public Class<GetPetByIdRequest> getRequestType() {
        return GetPetByIdRequest.class;
    }
}
