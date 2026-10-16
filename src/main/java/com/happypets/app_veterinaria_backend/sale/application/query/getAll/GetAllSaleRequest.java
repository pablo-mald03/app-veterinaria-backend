package com.happypets.app_veterinaria_backend.sale.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Principal get all sale request application layer
 *
 */
@Getter
@AllArgsConstructor
public class GetAllSaleRequest implements Request<GetAllSaleResponse>, AuditableRequest {

    private String billNumber;
    private Long userId;
    private SaleStatus status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Boolean withDetails;
    private PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "VENTAS";
    }

    @Override
    public String getAction() {
        return "CONSULTAR VENTAS";
    }

    @Override
    public String getDetail(Object response) {
        GetAllSaleResponse result = (GetAllSaleResponse) response;
        return "Se obtuvieron '" + result.getSales().getContent().size() + "' ventas";
    }
}