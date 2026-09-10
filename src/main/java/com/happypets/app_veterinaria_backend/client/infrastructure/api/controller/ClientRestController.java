package com.happypets.app_veterinaria_backend.client.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * Principal contest for clients rest controller
 *
 */
public interface ClientRestController {

    ResponseEntity<ClientResponseDTO> register(@Valid @RequestBody ClientRequestDTO clientRequestDTO);

    ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id);

    ResponseEntity<List<ClientResponseDTO>> findAll();

    ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO requestDTO);

    ResponseEntity<Void> delete(@PathVariable Long id);
}
