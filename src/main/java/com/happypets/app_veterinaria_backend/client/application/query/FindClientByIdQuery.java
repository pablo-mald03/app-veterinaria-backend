package com.happypets.app_veterinaria_backend.client.application.query;

import com.happypets.app_veterinaria_backend.client.application.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindClientByIdQuery implements Request<ClientResponseDTO> {
    private final Long id;
}
