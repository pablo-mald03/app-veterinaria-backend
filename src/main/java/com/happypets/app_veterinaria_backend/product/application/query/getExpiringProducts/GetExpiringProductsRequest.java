package com.happypets.app_veterinaria_backend.product.application.query.getExpiringProducts;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal expiring products request class
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetExpiringProductsRequest implements Request<GetExpiringProductsResponse>, AuditableRequest {
    private int days;
    private PaginationQuery paginationQuery;

    @Override
    public String getModule() {
        return "PRODUCTO";
    }

    @Override
    public String getAction() {
        return "CONSULTAR PRODUCTOS PROXIMOS A VENCER";
    }

    @Override
    public String getDetail(Object response) {
        GetExpiringProductsResponse result = (GetExpiringProductsResponse) response;
        return "Se obtuvieron '" + result.getContent().getContent().size()
                + "' productos que vencen en los proximos " + days + " dias";
    }
}