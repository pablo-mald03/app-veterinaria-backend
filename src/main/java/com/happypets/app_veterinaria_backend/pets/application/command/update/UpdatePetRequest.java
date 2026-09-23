package com.happypets.app_veterinaria_backend.pets.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.Data;

@Data
public class UpdatePetRequest implements Request<Void> {

    private Long idPet;
    private String name;
    private int  age;
    private double weight;
    private String description;

}
