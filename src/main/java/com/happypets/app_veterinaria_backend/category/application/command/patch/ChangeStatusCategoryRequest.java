package com.happypets.app_veterinaria_backend.category.application.command.patch;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request to change category status
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusCategoryRequest implements Request<Void>, AuditableRequest {
    private Long id;
    private Boolean status;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "CATEGORIAS";
    }

    /**
     * Log action
     */
    @Override
    public String getAction() {
        return "CAMBIO DE ESTADO DE CATEGORIA";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        String statusText = Boolean.TRUE.equals(this.status) ? "ACTIVO" : "INACTIVO";
        return "Se cambio el estado de la categoria ID: '" + this.id + "' a " + statusText;
    }
}