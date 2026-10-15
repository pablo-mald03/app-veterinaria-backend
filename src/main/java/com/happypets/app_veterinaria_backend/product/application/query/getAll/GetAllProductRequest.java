package com.happypets.app_veterinaria_backend.product.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get all products request
 *
 */
@Data
@AllArgsConstructor
public class GetAllProductRequest implements Request<GetAllProductResponse>, AuditableRequest {
    private Long priceFrom;
    private Long priceTo;
    private Long categoryId;
    private String sku;
    private String provider;
    private PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "PRODUCTOS";
    }

    @Override
    public String getAction() {
        return "CONSULTAR PRODUCTOS";
    }

    @Override
    public String getDetail(Object response) {
        GetAllProductResponse getAllProductResponse = (GetAllProductResponse) response;
        return "Se obtuvieron '" + getAllProductResponse.getProducts().getContent().size() + "' productos";
    }
}