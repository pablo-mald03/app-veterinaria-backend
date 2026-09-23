package com.happypets.app_veterinaria_backend.client.application.handler;

import com.happypets.app_veterinaria_backend.client.application.command.RegisterClientCommand;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.mapper.ClientDTOMapper;
import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterClientHandler implements RequestHandler<RegisterClientCommand, ClientResponseDTO> {
    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientDTOMapper clientDTOMapper;

    @Override
    public ClientResponseDTO handle(RegisterClientCommand command) {
        var dto =  command.getData();

        Client newClient = clientDTOMapper.toDomain(command.getData());

        Client savedClient = clientRepositoryPort.save(newClient);

        return clientDTOMapper.toDTO(savedClient);
    }

    @Override
    public Class<RegisterClientCommand> getRequestType() {
        return RegisterClientCommand.class;
    }
}
