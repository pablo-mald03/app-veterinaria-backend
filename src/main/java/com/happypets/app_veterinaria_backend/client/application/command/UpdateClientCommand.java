package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;

public record UpdateClientCommand(Long id, ClientRequestDTO clientRequestDTO) implements Request<ClientResponseDTO> {
}
