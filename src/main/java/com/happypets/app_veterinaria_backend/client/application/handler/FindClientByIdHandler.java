package com.happypets.app_veterinaria_backend.client.application.handler;

import com.happypets.app_veterinaria_backend.client.application.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.application.mapper.ClientDTOMapper;
import com.happypets.app_veterinaria_backend.client.application.query.FindClientByIdQuery;
import com.happypets.app_veterinaria_backend.client.domain.excepcions.ClientNotFoundException;
import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindClientByIdHandler implements RequestHandler<FindClientByIdQuery, ClientResponseDTO> {
    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientDTOMapper clientDTOMapper;

    @Override
    public ClientResponseDTO handle(FindClientByIdQuery query) {
        Client client = clientRepositoryPort.findById(query.getId())
                .orElseThrow(() -> new ClientNotFoundException(query.getId()));

        return clientDTOMapper.toDTO(client);
    }

    @Override
    public Class<FindClientByIdQuery> getRequestType() {
        return FindClientByIdQuery.class;
    }
}
