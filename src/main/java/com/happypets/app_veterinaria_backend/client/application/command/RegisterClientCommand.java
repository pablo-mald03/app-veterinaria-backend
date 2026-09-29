package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterClientCommand implements Request<ClientResponseDTO>, AuditableRequest {
    private final ClientRequestDTO data;

    @Override
    public String getModule() {
        return "CLIENTES";
    }

    @Override
    public String getAction() {
        return "REGISTRAR CLIENTE";
    }

    @Override
    public String getDetail(Object response) {
        return "Se registro un nuevo cliente con DPI: '" + data.getDpi() + "'";
    }
}
