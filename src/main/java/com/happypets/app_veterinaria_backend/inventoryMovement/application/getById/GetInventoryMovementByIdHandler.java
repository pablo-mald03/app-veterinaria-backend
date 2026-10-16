package com.happypets.app_veterinaria_backend.inventoryMovement.application.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.exception.InventoryMovementNotFoundException;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.port.InventoryMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get inventory movement by id handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetInventoryMovementByIdHandler implements RequestHandler<GetInventoryMovementByIdRequest, GetInventoryMovementByIdResponse> {

    private final InventoryMovementRepositoryPort inventoryMovementRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetInventoryMovementByIdResponse handle(GetInventoryMovementByIdRequest request) {

        InventoryMovement movement = inventoryMovementRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new InventoryMovementNotFoundException("No se encontró el movimiento de inventario con ID " + request.getId()));

        return new GetInventoryMovementByIdResponse(movement);
    }

    @Override
    public Class<GetInventoryMovementByIdRequest> getRequestType() {
        return GetInventoryMovementByIdRequest.class;
    }
}