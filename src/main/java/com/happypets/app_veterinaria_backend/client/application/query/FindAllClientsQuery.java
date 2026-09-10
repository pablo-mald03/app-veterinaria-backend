package com.happypets.app_veterinaria_backend.client.application.query;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;

import java.util.List;

public record FindAllClientsQuery() implements Request<List<ClientResponseDTO>> {
}
