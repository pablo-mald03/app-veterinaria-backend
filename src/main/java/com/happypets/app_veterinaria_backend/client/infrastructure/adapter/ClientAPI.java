package com.happypets.app_veterinaria_backend.client.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/clients")
@Tag(name = "Gestión de Clientes", description = "Endpoints para administrar a los dueños de las mascotas")
public interface ClientAPI {
    @Operation(summary = "Registrar un nuevo cliente", description = "Valida y guarda un cliente en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    ResponseEntity<ClientResponseDTO> register(@Valid @RequestBody ClientRequestDTO clientRequestDTO);

    @Operation(summary = "Consultar cliente por ID", description = "Obtiene los detalles de un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id);

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista completa de todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @GetMapping
    ResponseEntity<List<ClientResponseDTO>> findAll();

    @Operation(summary = "Actualizar un cliente", description = "Sobrescribe los datos de un cliente existente buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO requestDTO);

    @Operation(summary = "Eliminar un cliente", description = "Elimina físicamente a un cliente de la base de datos mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
