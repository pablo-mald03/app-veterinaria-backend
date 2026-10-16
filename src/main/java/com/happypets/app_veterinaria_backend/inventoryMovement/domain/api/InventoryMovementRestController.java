package com.happypets.app_veterinaria_backend.inventoryMovement.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.request.GetAllInventoryMovementQueryDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response.GetAllInventoryMovementResponseDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response.InventoryMovementResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Principal inventory movement controller contest
 *
 */
public interface InventoryMovementRestController {

    ResponseEntity<InventoryMovementResponseDto> getById(@PathVariable Long id);

    ResponseEntity<GetAllInventoryMovementResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery,
                                                              @ParameterObject GetAllInventoryMovementQueryDto queryDto);
}
