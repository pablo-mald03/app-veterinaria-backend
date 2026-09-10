package com.happypets.app_veterinaria_backend.pets.infrastructure.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetResponse;
import com.happypets.app_veterinaria_backend.pets.application.command.delete.DeletePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.command.update.UpdatePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.query.getall.GetAllPetRequest;
import com.happypets.app_veterinaria_backend.pets.application.query.getall.GetAllPetResponse;
import com.happypets.app_veterinaria_backend.pets.application.query.getbyid.GetPetByIdRequest;
import com.happypets.app_veterinaria_backend.pets.application.query.getbyid.GetPetByIdResponse;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetRequestDTO;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetResponseDTO;
import com.happypets.app_veterinaria_backend.pets.infrastructure.mapper.PetMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@AllArgsConstructor
@RequestMapping("/pets")
@Tag(name = "Pets", description = "Endpoints to manage pets")
@SecurityRequirement(name = "cookieAuth")
@Slf4j
public class PetController implements PetRestController {

    private final Mediator mediator;
    private final PetMapper petMapper;

    @Override
    public ResponseEntity<PaginationResult<PetResponseDTO>> getAllPets(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        log.info("Getting all pets");

        PaginationQuery paginationQuery = new PaginationQuery(pageNumber, pageSize, sortBy, direction);

        GetAllPetResponse response = mediator.dispatch(new GetAllPetRequest(paginationQuery));

        PaginationResult<Pet> pets = response.getPetPage();

        PaginationResult<PetResponseDTO> petsDtoPage = new PaginationResult<>(
                pets.getContent().stream().map(petMapper::toResponseDTO).toList(),
                pets.getPage(),
                pets.getSize(),
                pets.getTotalPages(),
                pets.getTotalElements()
        );

        return ResponseEntity.ok(petsDtoPage);
    }

    @Operation(summary = "Get product by id", description = "Get product by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id) {

        log.info("Getting pet with id: {}", id);

        GetPetByIdResponse response = mediator.dispatch(new GetPetByIdRequest(id));


        PetResponseDTO pet = petMapper.toResponseDTO(response.getPet());

        log.info("Found pet with id: {}", pet.getIdPet());
        return ResponseEntity.ok(pet);
    }

    @Operation(summary = "Save pet", description = "Save pet create")
    @PostMapping("/create")
    @Override
    public ResponseEntity<Void> savePet(@ModelAttribute @Valid PetRequestDTO pet) {

        CreatePetRequest request = petMapper.toCreateRequest(pet);
        CreatePetResponse response = mediator.dispatch(request);

        Pet savedPet = response.getPet();
        log.info("Pet with id {} was saved", savedPet.getIdPet());

        return ResponseEntity.created(URI.create("/pets/".concat(savedPet.getIdPet().toString()))).build();
    }

    @Operation(summary = "Update pet", description = "Update an existing pet")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Void> updatePet(@PathVariable Long id, @RequestBody @Valid PetRequestDTO pet) {
        log.info("Updating pet with id {}", id);

        UpdatePetRequest request = petMapper.toUpdateRequest(id, pet);
        mediator.dispatch(request);

        log.info("Pet with id {} was updated", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete pet", description = "Delete a pet by id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        log.info("Deleting pet with id {}", id);

        mediator.dispatchAsync(new DeletePetRequest(id));

        log.info("Pet with id {} accepted for deletion", id);
        return ResponseEntity.accepted().build();
    }
}
