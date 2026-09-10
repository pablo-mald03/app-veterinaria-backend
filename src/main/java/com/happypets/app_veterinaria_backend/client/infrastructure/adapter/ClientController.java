package com.happypets.app_veterinaria_backend.client.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.client.application.command.DeleteClientByIdCommand;
import com.happypets.app_veterinaria_backend.client.application.command.RegisterClientCommand;
import com.happypets.app_veterinaria_backend.client.application.command.UpdateClientCommand;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.application.query.FindAllClientsQuery;
import com.happypets.app_veterinaria_backend.client.application.query.FindClientByIdQuery;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ClientController implements ClientAPI{
    private final Mediator mediator;

    @Override
    public ResponseEntity<ClientResponseDTO> register(ClientRequestDTO clientRequestDTO) {
        var command = new RegisterClientCommand(clientRequestDTO);
        ClientResponseDTO response = mediator.dispatch(command);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ClientResponseDTO> findById(Long id) {
        var query = new FindClientByIdQuery(id);
        ClientResponseDTO response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        var query = new FindAllClientsQuery();
        List<ClientResponseDTO> response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ClientResponseDTO> update(Long id, ClientRequestDTO requestDTO) {
        var command = new UpdateClientCommand(id, requestDTO);
        ClientResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var command = new DeleteClientByIdCommand(id);
        mediator.dispatch(command);
        return ResponseEntity.noContent().build();
    }
}
