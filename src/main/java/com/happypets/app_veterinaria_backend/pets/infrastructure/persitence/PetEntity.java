package com.happypets.app_veterinaria_backend.pets.infrastructure.persitence;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pet")
public class PetEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPet;

    private String name;
    private String breed;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    private String color;
    private int age;
    private double weight;
    private String species;
    private String description;

}
