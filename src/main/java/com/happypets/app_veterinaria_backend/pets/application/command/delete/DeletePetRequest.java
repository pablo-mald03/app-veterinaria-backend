package com.happypets.app_veterinaria_backend.pets.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePetRequest implements Request<Void> {

    private Long idPet;

}
