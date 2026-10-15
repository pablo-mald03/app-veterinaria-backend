package com.happypets.app_veterinaria_backend.category.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update category application layer request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest implements Request<UpdateCategoryResponse>, AuditableRequest {

    private Long id;
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
        return "ACTUALIZACION DE CATEGORIA";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        UpdateCategoryResponse result = (UpdateCategoryResponse) response;
        return "Se actualizo la categoria ID: '" + this.id + "' a '" + result.getName() + "'";
    }
}