package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterClientCommand implements Request<ClientResponseDTO> {
    private final ClientRequestDTO data;
}
