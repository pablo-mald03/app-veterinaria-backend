package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientByIdCommand implements Request<Boolean>, AuditableRequest {
    private Long id;

    @Override
    public String getModule() {
        return "CLIENTES";
    }

    @Override
    public String getAction() {
        return "ELIMINAR CLIENTE";
    }

    @Override
    public String getDetail(Object response) {
        return "Se ejecuto la accion de eliminar al cliente con ID: '" + id + "'";
    }
}
