package com.happypets.app_veterinaria_backend.client.application.query;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindClientByIdQuery implements Request<ClientResponseDTO>, AuditableRequest {
    private final Long id;

    @Override
    public String getModule() {
        return "CLIENTES";
    }

    @Override
    public String getAction() {
        return "OBTENER CLIENTE POR ID";
    }

    @Override
    public String getDetail(Object response) {
        return "Se obtuvo la informacion del cliente con ID: '" + id + "'";
    }
}
