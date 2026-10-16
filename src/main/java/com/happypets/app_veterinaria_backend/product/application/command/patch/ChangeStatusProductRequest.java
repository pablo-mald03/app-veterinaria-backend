package com.happypets.app_veterinaria_backend.product.application.command.patch;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal change status product request
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusProductRequest implements Request<Void>, AuditableRequest {
    private Long id;
    private Boolean status;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "PRODUCTOS";
    }

    /**
     * Log action
     *
     */
    @Override
    public String getAction() {
        return "CAMBIO DE ESTADO DE PRODUCTO";
    }

    /**
     * Log detail
     *
     */
    @Override
    public String getDetail(Object response) {
        String statusText = Boolean.TRUE.equals(status) ? "ACTIVO" : "INACTIVO";
        return "Se cambio el estado del producto ID: '" + id + "' a " + statusText;
    }
}