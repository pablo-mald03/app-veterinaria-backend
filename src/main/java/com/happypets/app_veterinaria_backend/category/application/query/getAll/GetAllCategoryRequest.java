package com.happypets.app_veterinaria_backend.category.application.query.getAll;

import com.pablocompany.proyecto.no1.archivos.app_proyecto_no1_backend.common.application.mediator.Request;
import com.pablocompany.proyecto.no1.archivos.app_proyecto_no1_backend.common.domain.auditable.AuditableRequest;
import com.pablocompany.proyecto.no1.archivos.app_proyecto_no1_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all categories request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCategoryRequest implements Request<GetAllCategoryResponse>, AuditableRequest {
    private Boolean status;
    private PaginationQuery paginationQuery;

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
        return "CONSULTAR CATEGORIAS";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllCategoryResponse result = (GetAllCategoryResponse) response;
        return "Se obtuvieron '" + result.getCategories().getContent().size() + "' categorias";
    }
}