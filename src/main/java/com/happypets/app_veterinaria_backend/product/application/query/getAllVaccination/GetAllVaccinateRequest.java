package com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination;

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
public class GetAllVaccinateRequest implements Request<GetAllVaccinateResponse>, AuditableRequest {
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
        return "CONSULTAR VACUNAS";
    }

    @Override
    public String getDetail(Object response) {
        GetAllVaccinateResponse getAllVaccinateResponse = (GetAllVaccinateResponse) response;
        return "Se obtuvieron '" + getAllVaccinateResponse.getVaccinations().getContent().size() + "' vacunas";
    }
}