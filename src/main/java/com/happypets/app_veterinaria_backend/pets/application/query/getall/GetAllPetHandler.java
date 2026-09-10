package com.happypets.app_veterinaria_backend.pets.application.query.getall;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllPetHandler implements RequestHandler<GetAllPetRequest, GetAllPetResponse> {


    private final PetRepository petRepository;


    @Override
    public GetAllPetResponse handle(GetAllPetRequest request) {

        log.info("Getting all pets");

        PaginationResult<Pet> pets = petRepository.findAll(request.getPaginationQuery());

        return new GetAllPetResponse(pets);
    }

    @Override
    public Class<GetAllPetRequest> getRequestType() {
        return GetAllPetRequest.class;
    }
}
