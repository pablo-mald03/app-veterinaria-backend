package com.happypets.app_veterinaria_backend.inventoryBatch.application.getLow;


import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get low stock products request handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetLowStockProductsRequestHandler implements RequestHandler<GetLowStockProductsRequest, GetLowStockProductsResponse> {

    private final InventoryBatchRepositoryPort batchRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetLowStockProductsResponse handle(GetLowStockProductsRequest request) {
        PaginationResult<LowStockProductSummary> result = batchRepositoryPort.findLowStockProducts(
                request.getPaginationQuery()
        );
        return new GetLowStockProductsResponse(result);
    }

    @Override
    public Class<GetLowStockProductsRequest> getRequestType() {
        return GetLowStockProductsRequest.class;
    }
}