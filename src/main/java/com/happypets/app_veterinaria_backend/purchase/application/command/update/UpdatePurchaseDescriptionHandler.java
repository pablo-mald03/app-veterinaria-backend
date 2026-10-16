package com.happypets.app_veterinaria_backend.purchase.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.purchase.domain.exception.PurchaseNotFoundException;
import com.happypets.app_veterinaria_backend.purchase.domain.port.PurchaseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Update purchase description handler
 *
 */
@Service
@RequiredArgsConstructor
public class UpdatePurchaseDescriptionHandler implements RequestHandler<UpdatePurchaseDescriptionRequest, Void> {

    private final PurchaseRepositoryPort purchaseRepositoryPort;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void handle(UpdatePurchaseDescriptionRequest request) {

        if (!purchaseRepositoryPort.existsById(request.getId())) {
            throw new PurchaseNotFoundException("No se encontró la compra con ID " + request.getId());
        }

        purchaseRepositoryPort.updateDescription(request.getId(), request.getDescription());

        return null;
    }

    @Override
    public Class<UpdatePurchaseDescriptionRequest> getRequestType() {
        return UpdatePurchaseDescriptionRequest.class;
    }
}