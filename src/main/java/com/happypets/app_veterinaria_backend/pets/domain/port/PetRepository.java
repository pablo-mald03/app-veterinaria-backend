package com.happypets.app_veterinaria_backend.pets.domain.port;

import com.happypets.app_veterinaria_backend.common.domain.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;

import java.util.Optional;

public interface PetRepository {

    Pet save(Pet pet);
    Optional<Pet> findById(Long id);
    PaginationResult<Pet> findAll(PaginationQuery paginationQuery);
    Pet update(Pet pet);
    void deleteById(Long id);
}
