package com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all product stock summary request class
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductStockSummaryRequest implements Request<GetAllProductStockSummaryResponse>, AuditableRequest {
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
        return "CONSULTAR RESUMEN DE STOCK";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllProductStockSummaryResponse result = (GetAllProductStockSummaryResponse) response;
        return "Se obtuvieron '" + result.getSummaries().getContent().size() + "' resumenes de stock de productos";
    }
}