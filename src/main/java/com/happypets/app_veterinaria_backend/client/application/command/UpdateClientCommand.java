package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientCommand implements Request<ClientResponseDTO>, AuditableRequest {

    private Long id;
    private ClientRequestDTO clientRequestDTO;

    @Override
    public String getModule() {
        return "CLIENTES";
    }

    @Override
    public String getAction() {
        return "MODIFICAR CLIENTE";
    }

    @Override
    public String getDetail(Object response) {
        return "Se modifico la informacion del cliente con ID: '" + id + "'";
    }
}