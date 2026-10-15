package com.happypets.app_veterinaria_backend.category.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create category class request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest implements Request<CreateCategoryResponse>, AuditableRequest {
    private String name;
    private String description;

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
        return "CREACION DE CATEGORIA";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        return "Se creo una nueva categoria '" + this.name + "' en el sistema";
    }
}