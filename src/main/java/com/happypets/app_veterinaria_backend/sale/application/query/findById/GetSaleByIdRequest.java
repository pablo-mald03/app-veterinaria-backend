package com.happypets.app_veterinaria_backend.sale.application.query.findById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get sale by id request
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSaleByIdRequest implements Request<GetSaleByIdResponse>, AuditableRequest {

    private Long id;

    @Override
    public String getModule() {
        return "VENTAS";
    }

    @Override
    public String getAction() {
        return "CONSULTAR VENTA POR ID";
    }

    @Override
    public String getDetail(Object response) {
        GetSaleByIdResponse result = (GetSaleByIdResponse) response;
        return "Se consultó la venta ID: " + result.getSale().getId() + " con factura: " + result.getSale().getBillNumber();
    }
}