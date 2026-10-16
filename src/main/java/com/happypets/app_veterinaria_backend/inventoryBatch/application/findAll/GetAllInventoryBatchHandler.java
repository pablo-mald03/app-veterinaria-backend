package com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll;


import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.filter.InventoryBatchFilter;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Principal get all inventory batch handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllInventoryBatchHandler implements RequestHandler<GetAllInventoryBatchRequest, GetAllInventoryBatchResponse> {

    private final InventoryBatchRepositoryPort inventoryBatchRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetAllInventoryBatchResponse handle(GetAllInventoryBatchRequest request) {

        InventoryBatchFilter filter = InventoryBatchFilter.builder()
                .productId(request.getProductId())
                .status(request.getStatus())
                .onlyAvailable(request.getOnlyAvailable())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build();

        PaginationResult<InventoryBatch> domainResult = inventoryBatchRepositoryPort.findAll(filter, request.getPaginationQuery());

        return new GetAllInventoryBatchResponse(domainResult);
    }

    @Override
    public Class<GetAllInventoryBatchRequest> getRequestType() {
        return GetAllInventoryBatchRequest.class;
    }
}