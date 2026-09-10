package com.happypets.app_veterinaria_backend.pets.application.query.getall;

import com.happypets.app_veterinaria_backend.common.domain.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetAllPetResponse {

    private PaginationResult<Pet> petPage;
}
