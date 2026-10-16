package com.happypets.app_veterinaria_backend.sale.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Principal create sale request
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSaleRequest implements Request<CreateSaleResponse>, AuditableRequest {

    private String billNumber;
    private LocalDateTime date;
    private BigDecimal iva;
    private List<CreateSaleItemRequest> items;

    @Override
    public String getModule() {
        return "VENTAS";
    }

    @Override
    public String getAction() {
        return "REGISTRO DE VENTA";
    }

    @Override
    public String getDetail(Object response) {
        if (response instanceof CreateSaleResponse saleResponse && saleResponse.getSale() != null) {
            return "Se registró la venta ID " + saleResponse.getSale().getId() +
                    " con un total de Q" + saleResponse.getSale().getTotal();
        }
        return "Se registró una nueva venta ";
    }
}