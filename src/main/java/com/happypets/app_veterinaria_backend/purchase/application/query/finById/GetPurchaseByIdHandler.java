package com.happypets.app_veterinaria_backend.purchase.application.query.finById;


import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.exception.PurchaseNotFoundException;
import com.happypets.app_veterinaria_backend.purchase.domain.port.PurchaseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get purchases by id handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetPurchaseByIdHandler implements RequestHandler<GetPurchaseByIdRequest, GetPurchaseByIdResponse> {

    private final PurchaseRepositoryPort purchaseRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetPurchaseByIdResponse handle(GetPurchaseByIdRequest request) {

        Purchase purchase = purchaseRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new PurchaseNotFoundException("No se encontró la compra con ID " + request.getId()));

        return new GetPurchaseByIdResponse(purchase);
    }

    @Override
    public Class<GetPurchaseByIdRequest> getRequestType() {
        return GetPurchaseByIdRequest.class;
    }
}