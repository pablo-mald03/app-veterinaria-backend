package com.happypets.app_veterinaria_backend.inventoryBatch.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.request.GetAllInventoryBatchQueryDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.GetAllInventoryBatchResponseDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.GetAllProductStockSummaryResponseDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.InventoryBatchResponseDto;
import org.springframework.http.ResponseEntity;


/**
 * Principal inventory batch rest controller contest
 *
 */
public interface InventoryBatchRestController {

    ResponseEntity<InventoryBatchResponseDto> getById(Long id);

    ResponseEntity<GetAllInventoryBatchResponseDto> getAll(PaginationQuery paginationQuery, GetAllInventoryBatchQueryDto queryDto);

    ResponseEntity<GetAllProductStockSummaryResponseDto> getAllStockSummary(PaginationQuery paginationQuery);
}