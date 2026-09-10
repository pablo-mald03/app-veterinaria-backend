package com.happypets.app_veterinaria_backend.pets.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.Data;

@Data
public class CreatePetRequest implements Request<CreatePetResponse> {

    private String name;
    private String breed;
    private Long idClient;
    private String color;
    private int  age;
    private double weight;
    private String species;
    private String description;
}
