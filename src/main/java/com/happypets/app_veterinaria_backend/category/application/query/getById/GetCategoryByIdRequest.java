package com.happypets.app_veterinaria_backend.category.application.query.getById;


import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get category by id application layer request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryByIdRequest implements Request<GetCategoryByIdResponse>, AuditableRequest {

    private Long id;

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
        return "CONSULTAR CATEGORIAS POR ID";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetCategoryByIdResponse result = (GetCategoryByIdResponse) response;
        return "Se obtuvo la categoria: '" + result.getId() + "'";
    }
}