package com.happypets.app_veterinaria_backend.product.application.query.getDewormer;

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
public class GetAllDewormerRequest implements Request<GetAllDewormerResponse>, AuditableRequest {
    private Long priceFrom;
    private Long priceTo;
    private String sku;
    private String provider;
    private PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "PRODUCTOS";
    }

    @Override
    public String getAction() {
        return "CONSULTAR DESPARASITANTES";
    }

    @Override
    public String getDetail(Object response) {
        GetAllDewormerResponse getAllVaccinateResponse = (GetAllDewormerResponse) response;
        return "Se obtuvieron '" + getAllVaccinateResponse.getVaccinations().getContent().size() + "' desparasitantes";
    }
}