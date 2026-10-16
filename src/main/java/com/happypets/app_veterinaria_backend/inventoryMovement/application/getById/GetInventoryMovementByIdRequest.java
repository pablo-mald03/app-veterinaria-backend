package com.happypets.app_veterinaria_backend.inventoryMovement.application.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get inventory movement request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInventoryMovementByIdRequest implements Request<GetInventoryMovementByIdResponse>, AuditableRequest {
    private Long id;

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
        return "CONSULTA DE MOVIMIENTO DE INVENTARIO POR ID";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetInventoryMovementByIdResponse result = (GetInventoryMovementByIdResponse) response;
        return "Se consulto la informacion del movimiento de inventario con ID: '" + result.getMovement().getId() + "'";
    }
}