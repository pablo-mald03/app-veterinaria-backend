package com.happypets.app_veterinaria_backend.pets.infrastructure.controller;

import com.happypets.app_veterinaria_backend.common.domain.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.application.dto.PetRequestDTO;
import com.happypets.app_veterinaria_backend.pets.application.dto.PetResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PetRestController {

    ResponseEntity<PaginationResult<PetResponseDTO>> getAllPets(int pageNumber, int pageSize, String sortBy, String direction);

    ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id);

    ResponseEntity<Void> savePet(@RequestBody @Valid PetRequestDTO pet);

    ResponseEntity<Void> updatePet(@PathVariable Long id, @RequestBody @Valid PetRequestDTO pet);

    ResponseEntity<Void> deletePet(@PathVariable Long id);
}

