package com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Principal get all inventory batch request
 *
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInventoryBatchRequest implements Request<GetAllInventoryBatchResponse>, AuditableRequest {
    private Long productId;
    private Boolean status;
    private Boolean onlyAvailable;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
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
        return "CONSULTAR LOTE DE INVENTARIO";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllInventoryBatchResponse result = (GetAllInventoryBatchResponse) response;
        return "Se obtuvieron '" + result.getBatches().getContent().size() + "' lotes de inventario";
    }
}