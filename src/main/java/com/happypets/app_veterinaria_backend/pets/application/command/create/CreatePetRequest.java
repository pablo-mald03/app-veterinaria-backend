package com.happypets.app_veterinaria_backend.pets.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.Data;

@Data
public class CreatePetRequest implements Request<CreatePetResponse>, AuditableRequest {

    private String name;
    private String breed;
    private Long idClient;
    private String color;
    private int age;
    private double weight;
    private String species;
    private String description;

    @Override
    public String getModule() {
        return "MASCOTAS";
    }

    @Override
    public String getAction() {
        return "CREAR MASCOTA";
    }

    @Override
    public String getDetail(Object response) {
        CreatePetResponse result = (CreatePetResponse) response;
        return "Se registro la mascota con ID: " + result.getPet().getIdPet();
    }
}
