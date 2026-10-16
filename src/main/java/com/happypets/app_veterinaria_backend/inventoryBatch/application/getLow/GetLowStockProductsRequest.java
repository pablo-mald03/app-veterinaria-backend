package com.happypets.app_veterinaria_backend.inventoryBatch.application.getLow;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get low stock product request
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLowStockProductsRequest implements Request<GetLowStockProductsResponse>, AuditableRequest {
    private PaginationQuery paginationQuery;

    /**
     * Audit log module
     */
    @Override
    public String getModule() {
        return "LOTE_INVENTARIO";
    }

    /**
     * Audit log action
     */
    @Override
    public String getAction() {
        return "CONSULTAR LOTE DE INVENTARIO BAJO";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetLowStockProductsResponse result = (GetLowStockProductsResponse) response;
        return "Se obtuvieron '" + result.getContent().getContent().size() + "' lotes bajos de inventario";
    }
}