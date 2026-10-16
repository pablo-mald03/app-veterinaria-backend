package com.happypets.app_veterinaria_backend.purchase.application.query.getAll;


import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Principal get all purchase request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPurchaseRequest implements Request<GetAllPurchaseResponse>, AuditableRequest {
    private UUID userId;
    private PurchaseStatus status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Boolean withDetails;
    private PaginationQuery paginationQuery;

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
        return "CONSULTAR COMPRAS";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllPurchaseResponse result = (GetAllPurchaseResponse) response;
        return "Se obtuvieron '" + result.getPurchases().getContent().size() + "' compras";
    }
}