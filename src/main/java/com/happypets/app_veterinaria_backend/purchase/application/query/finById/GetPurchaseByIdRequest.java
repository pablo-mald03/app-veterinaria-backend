package com.happypets.app_veterinaria_backend.purchase.application.query.finById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get purchase by id request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPurchaseByIdRequest implements Request<GetPurchaseByIdResponse>, AuditableRequest {
    private Long id;

    /**
     * Audit log module
     */
    @Override
    public String getModule() {
        return "COMPRAS";
    }

    /**
     * Audit log action
     */
    @Override
    public String getAction() {
        return "CONSULTA DE COMPRA POR ID";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetPurchaseByIdResponse result = (GetPurchaseByIdResponse) response;
        return "Se consulto la informacion de la compra con ID: '" + result.getPurchase().getId() + "'";
    }
}