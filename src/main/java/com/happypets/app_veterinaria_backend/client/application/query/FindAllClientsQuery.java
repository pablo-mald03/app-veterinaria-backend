package com.happypets.app_veterinaria_backend.client.application.query;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FindAllClientsQuery implements Request<List<ClientResponseDTO>>, AuditableRequest {

    @Override
    public String getModule() {
        return "CLIENTES";
    }

    @Override
    public String getAction() {
        return "OBTENER CLIENTES";
    }

    @Override
    public String getDetail(Object response) {
        return "Se obtuvo el listado de clientes registrados en el sistema";
    }
}
