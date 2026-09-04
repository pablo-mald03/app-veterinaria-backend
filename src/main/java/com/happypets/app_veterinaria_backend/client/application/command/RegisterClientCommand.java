package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.client.application.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.application.dto.ClientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RegisterClientCommand implements Request<ClientResponseDTO>{
    private final ClientRequestDTO data;
}
