package com.happypets.app_veterinaria_backend.inventoryBatch.application.findById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Principal get by id inventory batch request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInventoryBatchByIdRequest implements Request<GetInventoryBatchByIdResponse>, AuditableRequest {
    private Long id;

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
        return "CONSULTA DE LOTE DE INVENTARIO POR ID";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetInventoryBatchByIdResponse result = (GetInventoryBatchByIdResponse) response;
        return "Se consulto la informacion del lote de inventario con ID: '" + result.getBatch().getId() + "'";
    }
}