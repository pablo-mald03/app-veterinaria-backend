package com.happypets.app_veterinaria_backend.client.application.handler;

import com.happypets.app_veterinaria_backend.client.application.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.application.mapper.ClientDTOMapper;
import com.happypets.app_veterinaria_backend.client.application.query.FindAllClientsQuery;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllClientsHandler implements RequestHandler<FindAllClientsQuery, List<ClientResponseDTO>> {
    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientDTOMapper clientDTOMapper;

    @Override
    public List<ClientResponseDTO> handle(FindAllClientsQuery query) {
        return clientRepositoryPort.findAll().stream().map(clientDTOMapper::toDTO).toList();
    }

    @Override
    public Class<FindAllClientsQuery> getRequestType() {
        return FindAllClientsQuery.class;
    }
}
