package com.happypets.app_veterinaria_backend.sale.application.query.findById;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.domain.exception.SaleNotFoundException;
import com.happypets.app_veterinaria_backend.sale.domain.port.SaleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get sale by id handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetSaleByIdHandler implements RequestHandler<GetSaleByIdRequest, GetSaleByIdResponse> {

    private final SaleRepositoryPort saleRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetSaleByIdResponse handle(GetSaleByIdRequest request) {
        Sale sale = saleRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new SaleNotFoundException("Venta no encontrada con ID: " + request.getId()));

        return new GetSaleByIdResponse(sale);
    }

    @Override
    public Class<GetSaleByIdRequest> getRequestType() {
        return GetSaleByIdRequest.class;
    }
}