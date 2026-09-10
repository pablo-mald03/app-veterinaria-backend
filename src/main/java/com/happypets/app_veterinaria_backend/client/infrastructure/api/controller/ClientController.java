package com.happypets.app_veterinaria_backend.client.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.client.application.command.DeleteClientByIdCommand;
import com.happypets.app_veterinaria_backend.client.application.command.RegisterClientCommand;
import com.happypets.app_veterinaria_backend.client.application.command.UpdateClientCommand;
import com.happypets.app_veterinaria_backend.client.application.query.FindAllClientsQuery;
import com.happypets.app_veterinaria_backend.client.application.query.FindClientByIdQuery;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Gestión de Clientes", description = "Endpoints para administrar a los dueños de las mascotas")
public class ClientController implements ClientRestController {
    private final Mediator mediator;

    @Operation(summary = "Registrar un nuevo cliente", description = "Valida y guarda un cliente en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    @Override
    public ResponseEntity<ClientResponseDTO> register(ClientRequestDTO clientRequestDTO) {
        var command = new RegisterClientCommand(clientRequestDTO);
        ClientResponseDTO response = mediator.dispatch(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar cliente por ID", description = "Obtiene los detalles de un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ClientResponseDTO> findById(Long id) {
        var query = new FindClientByIdQuery(id);
        ClientResponseDTO response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista completa de todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @GetMapping
    @Override
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        var query = new FindAllClientsQuery();
        List<ClientResponseDTO> response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar un cliente", description = "Sobrescribe los datos de un cliente existente buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ClientResponseDTO> update(Long id, ClientRequestDTO requestDTO) {
        var command = new UpdateClientCommand(id, requestDTO);
        ClientResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un cliente", description = "Elimina físicamente a un cliente de la base de datos mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var command = new DeleteClientByIdCommand(id);
        mediator.dispatch(command);
        return ResponseEntity.noContent().build();
    }
}
