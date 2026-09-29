package com.happypets.app_veterinaria_backend.pets.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.Data;

@Data
public class UpdatePetRequest implements Request<Void>, AuditableRequest {

    private Long idPet;
    private String name;
    private int age;
    private double weight;
    private String description;

    @Override
    public String getModule() {
        return "MASCOTAS";
    }

    @Override
    public String getAction() {
        return "MODIFICAR MASCOTA";
    }

    @Override
    public String getDetail(Object response) {
        return "Se ejecuto la accion de modificar mascota con ID: " + idPet;
    }
}
