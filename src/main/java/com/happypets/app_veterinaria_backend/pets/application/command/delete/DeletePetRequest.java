package com.happypets.app_veterinaria_backend.pets.application.command.delete;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePetRequest implements Request<Void>, AuditableRequest {

    private Long idPet;

    @Override
    public String getModule() {
        return "MASCOTAS";
    }

    @Override
    public String getAction() {
        return "ELIMINAR MASCOTA";
    }

    @Override
    public String getDetail(Object response) {
        return "Se ejecuto la accion de eliminar la mascota con ID: " + idPet;
    }
}
