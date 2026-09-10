package com.happypets.app_veterinaria_backend.client.application.handler;

import com.happypets.app_veterinaria_backend.client.application.command.DeleteClientByIdCommand;
import com.happypets.app_veterinaria_backend.client.domain.excepcions.ClientNotFoundException;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteClientByIdHandler implements RequestHandler<DeleteClientByIdCommand, Boolean> {
    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Boolean handle(final DeleteClientByIdCommand command) {
        clientRepositoryPort.findById(command.id()).orElseThrow(() -> new ClientNotFoundException(command.id()));

        clientRepositoryPort.deleteById(command.id());
        return true;
    }

    @Override
    public Class<DeleteClientByIdCommand> getRequestType() {
        return DeleteClientByIdCommand.class;
    }
}
