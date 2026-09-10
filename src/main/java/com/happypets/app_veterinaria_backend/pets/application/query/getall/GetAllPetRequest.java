package com.happypets.app_veterinaria_backend.pets.application.query.getall;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllPetRequest implements Request<GetAllPetResponse> {

    PaginationQuery paginationQuery;

}
