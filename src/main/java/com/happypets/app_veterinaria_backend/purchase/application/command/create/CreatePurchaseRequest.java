package com.happypets.app_veterinaria_backend.purchase.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create purchase request class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseRequest implements Request<CreatePurchaseResponse>, AuditableRequest {

    private String description;
    private BigDecimal iva;
    private List<CreatePurchaseDetailItemRequest> details;

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
        return "REGISTRO DE COMPRA";
    }

    /**
     * Audit log detail description
     */
    @Override
    public String getDetail(Object response) {
        if (response instanceof CreatePurchaseResponse purchaseResponse && purchaseResponse.getPurchase() != null) {
            return "Se registró la compra ID " + purchaseResponse.getPurchase().getId() +
                    " con un total de Q" + purchaseResponse.getPurchase().getTotal();
        }
        return "Se registró una nueva compra ";
    }
}