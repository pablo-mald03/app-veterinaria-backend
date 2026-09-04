package com.happypets.app_veterinaria_backend.client.application.handler;

import com.happypets.app_veterinaria_backend.client.application.command.RegisterClientCommand;
import com.happypets.app_veterinaria_backend.client.application.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.application.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;

public class RegisterClientHandler implements RequestHandeler<RegisterClientCommand, ClientResponseDTO>{
    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public ClientResponseDTO handle(RegisterClientCommand command) {
        var dto =  command.getData();

        Client newClient = Client.builder()
                .dpi(dto.dpi())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .phone(dto.phone())
                .address(dto.address())
                .build();

        Client savedClient = clientRepositoryPort.save(newClient);

        return new ClientResponseDTO()
    }
}
