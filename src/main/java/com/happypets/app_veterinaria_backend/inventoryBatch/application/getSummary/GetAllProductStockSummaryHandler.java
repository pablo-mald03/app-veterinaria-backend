package com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Get all product stock summary handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllProductStockSummaryHandler implements RequestHandler<GetAllProductStockSummaryRequest, GetAllProductStockSummaryResponse> {

    //Attributes
    private final InventoryBatchRepositoryPort inventoryBatchRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetAllProductStockSummaryResponse handle(GetAllProductStockSummaryRequest request) {
        PaginationResult<ProductStockSummary> summaries = inventoryBatchRepositoryPort.findAllSummary(request.getPaginationQuery());

        return new GetAllProductStockSummaryResponse(summaries);
    }

    @Override
    public Class<GetAllProductStockSummaryRequest> getRequestType() {
        return GetAllProductStockSummaryRequest.class;
    }
}