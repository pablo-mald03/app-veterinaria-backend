package com.happypets.app_veterinaria_backend.pets.domain.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Pet {

    private Long idPet;
    private String name;
    private String breed;
    private Long idClient;
    private String color;
    private int age;
    private double weight;
    private String species;
    private String description;

}
