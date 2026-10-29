package com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.filter.InventoryMovementFilter;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.port.InventoryMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get all inventory movement  handler
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllInventoryMovementHandler implements RequestHandler<GetAllInventoryMovementRequest, GetAllInventoryMovementResponse> {

    private final InventoryMovementRepositoryPort inventoryMovementRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetAllInventoryMovementResponse handle(GetAllInventoryMovementRequest request) {

        InventoryMovementFilter filter = InventoryMovementFilter.builder()
                .productId(request.getProductId())
                .batchId(request.getBatchId())
                .purchaseId(request.getPurchaseId())
                .saleId(request.getSaleId())
                .type(request.getType())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build();
        log.info("hola desde backend");

        PaginationResult<InventoryMovement> domainResult = inventoryMovementRepositoryPort.findAll(filter, request.getPaginationQuery());

        return new GetAllInventoryMovementResponse(domainResult);
    }

    @Override
    public Class<GetAllInventoryMovementRequest> getRequestType() {
        return GetAllInventoryMovementRequest.class;
    }
}