package com.happypets.app_veterinaria_backend.pets.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatePetHandler implements RequestHandler<CreatePetRequest, CreatePetResponse> {

    private final PetRepository petRepository;


    @Override
    public CreatePetResponse handle(CreatePetRequest request) {

        log.info("Create Pet Request: " + request);

        Pet pet = Pet.builder().name(request.getName()).
                breed(request.getBreed()).
                idClient(request.getIdClient()).
                color(request.getColor()).
                age(request.getAge()).
                weight(request.getWeight()).
                species(request.getSpecies()).
                description(request.getDescription()).
                build();

        Pet createdPet = petRepository.save(pet);
        log.info("Created Pet: " + createdPet);

        return new CreatePetResponse(createdPet);

    }

    @Override
    public Class<CreatePetRequest> getRequestType() {
        return CreatePetRequest.class;
    }
}
