package com.happypets.app_veterinaria_backend.pets.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetRequestDTO {

    private String name;
    private String breed;
    private Long idClient;
    private String color;
    private int  age;
    private double weight;
    private String species;
    private String description;
}
