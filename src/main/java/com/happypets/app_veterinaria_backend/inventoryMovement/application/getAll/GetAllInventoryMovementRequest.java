package com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Principal get all inventory movement request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInventoryMovementRequest implements Request<GetAllInventoryMovementResponse>, AuditableRequest {
    private Long productId;
    private Long batchId;
    private Long purchaseId;
    private Long saleId;
    private MovementType type;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private PaginationQuery paginationQuery;

    /**
     * Audit log module
     */
    @Override
    public String getModule() {
        return "MOVIMIENTO_INVENTARIO";
    }

    /**
     * Audit log action
     */
    @Override
    public String getAction() {
        return "CONSULTAR MOVIMIENTO DE INVENTARIO";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllInventoryMovementResponse result = (GetAllInventoryMovementResponse) response;
        return "Se obtuvieron '" + result.getMovements().getContent().size() + "' movimientos de inventario";
    }
}