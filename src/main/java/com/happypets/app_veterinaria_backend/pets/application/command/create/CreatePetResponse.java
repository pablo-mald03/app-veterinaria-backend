package com.happypets.app_veterinaria_backend.pets.application.command.create;

import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePetResponse {

    private Pet pet;

}
