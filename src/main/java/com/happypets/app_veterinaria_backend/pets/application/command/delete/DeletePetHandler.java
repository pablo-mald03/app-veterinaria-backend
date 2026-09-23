package com.happypets.app_veterinaria_backend.pets.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePetHandler implements RequestHandler<DeletePetRequest, Void> {

    private final PetRepository petRepository;

    @Override
    public Void handle(DeletePetRequest request) {

        System.out.println("Si entro a eliminar");

        try {
            Thread.sleep(5000);
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        petRepository.deleteById(request.getIdPet());

        System.out.println("Pet whit ID: " + request.getIdPet() + " delete");
        return null;

    }

    @Override
    public Class<DeletePetRequest> getRequestType() {
        return DeletePetRequest.class;
    }
}
