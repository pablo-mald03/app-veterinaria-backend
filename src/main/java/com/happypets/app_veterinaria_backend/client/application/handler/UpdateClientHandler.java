package com.happypets.app_veterinaria_backend.client.application.handler;

import com.happypets.app_veterinaria_backend.client.application.command.UpdateClientCommand;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.mapper.ClientDTOMapper;
import com.happypets.app_veterinaria_backend.client.domain.excepcions.ClientNotFoundException;
import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateClientHandler implements RequestHandler<UpdateClientCommand, ClientResponseDTO> {
    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientDTOMapper clientDTOMapper;

    @Override
    public ClientResponseDTO handle(UpdateClientCommand updateClientCommand) {
        Client existingClient = clientRepositoryPort.findById(updateClientCommand.id()).orElseThrow(() -> new ClientNotFoundException(updateClientCommand.id()));
        Client updateClient = clientDTOMapper.toDomain(updateClientCommand.clientRequestDTO());
        updateClient.setId(existingClient.getId());

        Client savedClient = clientRepositoryPort.update(updateClient);
        return clientDTOMapper.toDTO(savedClient);
    }

    @Override
    public Class<UpdateClientCommand> getRequestType() {
        return UpdateClientCommand.class;
    }
}
