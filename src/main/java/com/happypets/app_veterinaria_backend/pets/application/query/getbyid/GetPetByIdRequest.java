package com.happypets.app_veterinaria_backend.pets.application.query.getbyid;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

/*Tener un constructor con todos los parametros porque @Data NO LO GENERA*/
@AllArgsConstructor
public class GetPetByIdRequest implements Request<GetPetByIdResponse> {
    private Long id;
}
