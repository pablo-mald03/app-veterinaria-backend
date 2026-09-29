package com.happypets.app_veterinaria_backend.pets.application.query.getbyid;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

/*Tener un constructor con todos los parametros porque @Data NO LO GENERA*/
@AllArgsConstructor
public class GetPetByIdRequest implements Request<GetPetByIdResponse>, AuditableRequest {
    private Long id;

    @Override
    public String getModule() {
        return "MASCOTAS";
    }

    @Override
    public String getAction() {
        return "OBTENER MASCOTA POR ID";
    }

    @Override
    public String getDetail(Object response) {
        return "Se obtuvo la informacion de la mascota con ID: '" + id + "'";
    }
}
