package com.happypets.app_veterinaria_backend.inventoryBatch.application.findById;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.exception.InventoryBatchNotFoundException;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal find by id inventory batch handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetInventoryBatchByIdHandler implements RequestHandler<GetInventoryBatchByIdRequest, GetInventoryBatchByIdResponse> {

    private final InventoryBatchRepositoryPort inventoryBatchRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetInventoryBatchByIdResponse handle(GetInventoryBatchByIdRequest request) {
        InventoryBatch batch = inventoryBatchRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new InventoryBatchNotFoundException("No se encontró el lote de inventario con ID " + request.getId()));

        return new GetInventoryBatchByIdResponse(batch);
    }

    @Override
    public Class<GetInventoryBatchByIdRequest> getRequestType() {
        return GetInventoryBatchByIdRequest.class;
    }
}