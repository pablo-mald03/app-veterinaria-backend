package com.happypets.app_veterinaria_backend.pets.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PetResponseDTO {

    private Long idPet;
    private String name;
    private String breed;
    private Long idClient;
    private String color;
    private int  age;
    private double weight;
    private String species;
    private String description;

}
