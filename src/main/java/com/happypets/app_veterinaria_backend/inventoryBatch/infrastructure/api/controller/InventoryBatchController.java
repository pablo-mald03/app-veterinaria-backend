package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll.GetAllInventoryBatchRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll.GetAllInventoryBatchResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findById.GetInventoryBatchByIdRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findById.GetInventoryBatchByIdResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getLow.GetLowStockProductsRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getLow.GetLowStockProductsResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary.GetAllProductStockSummaryRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary.GetAllProductStockSummaryResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.api.InventoryBatchRestController;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.request.GetAllInventoryBatchQueryDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.GetAllInventoryBatchResponseDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.GetAllProductStockSummaryResponseDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.GetLowStockProductsResponseDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.InventoryBatchResponseDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.mapper.InventoryBatchMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory-batches")
@Tag(name = "Inventory Batch", description = "Module to manage the application inventory batches")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class InventoryBatchController implements InventoryBatchRestController {

    private final Mediator mediator;
    private final InventoryBatchMapper batchMapper;

    @PreAuthorize("hasAuthority('inventario:ver')")
    @Operation(summary = "Get inventory batch by id", description = "Fetch a single inventory batch by its unique ID")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<InventoryBatchResponseDto> getById(@PathVariable Long id) {
        GetInventoryBatchByIdRequest request = batchMapper.toGetInventoryBatchByIdRequest(id);
        GetInventoryBatchByIdResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(batchMapper.toInventoryBatchResponseDto(response));
    }

    /**
     * Endpoint to get all inventory batches with filters and pagination
     */
    @PreAuthorize("hasAuthority('inventario:ver')")
    @Operation(summary = "Get all inventory batches", description = "List all inventory batches using criteria filters and pagination")
    @GetMapping
    @Override
    public ResponseEntity<GetAllInventoryBatchResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery,
                                                                  @ParameterObject GetAllInventoryBatchQueryDto queryDto) {

        GetAllInventoryBatchRequest request = batchMapper.toGetAllInventoryBatchRequest(queryDto, paginationQuery);
        GetAllInventoryBatchResponse response = mediator.dispatch(request);

        return ResponseEntity.ok(batchMapper.toGetAllInventoryBatchResponseDto(response));
    }

    /**
     * Get all product stock summary with pageable params endpoint
     *
     */
    @PreAuthorize("hasAuthority('inventario:ver')")
    @Operation(summary = "Get product stock summary", description = "Get the pageable stock summary of the products")
    @GetMapping("/stock-summary")
    @Override
    public ResponseEntity<GetAllProductStockSummaryResponseDto> getAllStockSummary(@ParameterObject PaginationQuery paginationQuery) {
        GetAllProductStockSummaryRequest request = batchMapper.toGetAllProductStockSummaryRequest(paginationQuery);
        GetAllProductStockSummaryResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(batchMapper.toGetAllProductStockSummaryResponseDto(response));
    }

    @PreAuthorize("hasAuthority('inventario:ver')")
    @Operation(summary = "Get low stock product alerts", description = "List all products where total active batch stock is below or equal to the minimum stock threshold using pagination")
    @GetMapping("/lower")
    public ResponseEntity<GetLowStockProductsResponseDto> getLowStockAlerts(@ParameterObject PaginationQuery paginationQuery) {

        GetLowStockProductsRequest request = new GetLowStockProductsRequest(paginationQuery);
        GetLowStockProductsResponse response = mediator.dispatch(request);

        return ResponseEntity.ok(batchMapper.toGetLowStockListProductsResponseDto(response));
    }
}
