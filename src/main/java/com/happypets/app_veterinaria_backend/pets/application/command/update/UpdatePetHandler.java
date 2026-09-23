package com.happypets.app_veterinaria_backend.pets.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.pets.domain.exception.PetNotFoundException;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UpdatePetHandler implements RequestHandler<UpdatePetRequest, Void> {

    private final PetRepository petRepository;


    @Override
    public Void handle(UpdatePetRequest request) {
        Pet pet = petRepository.findById(request.getIdPet()).orElseThrow(() -> new PetNotFoundException(request.getIdPet()));

        pet.setName(request.getName());
        pet.setAge(request.getAge());
        pet.setWeight(request.getWeight());
        pet.setDescription(request.getDescription());

        petRepository.update(pet);

        log.info("Mascota actualizada, id: {}", pet.getIdPet());
        return null;
    }

    @Override
    public Class<UpdatePetRequest> getRequestType() {
        return UpdatePetRequest.class;
    }
}
