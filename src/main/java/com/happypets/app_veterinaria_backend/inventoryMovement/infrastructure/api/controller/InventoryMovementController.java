package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll.GetAllInventoryMovementRequest;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll.GetAllInventoryMovementResponse;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getById.GetInventoryMovementByIdRequest;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getById.GetInventoryMovementByIdResponse;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.api.InventoryMovementRestController;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.request.GetAllInventoryMovementQueryDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response.GetAllInventoryMovementResponseDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response.InventoryMovementResponseDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.mapper.InventoryMovementMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory-movements")
@Tag(name = "Inventory Movement", description = "Module to manage the application inventory movements")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class InventoryMovementController implements InventoryMovementRestController {

    private final Mediator mediator;
    private final InventoryMovementMapper movementMapper;

    @Operation(summary = "Get inventory movement by id", description = "Fetch a single inventory movement by its unique ID")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<InventoryMovementResponseDto> getById(@PathVariable Long id) {
        GetInventoryMovementByIdRequest request = movementMapper.toGetInventoryMovementByIdRequest(id);
        GetInventoryMovementByIdResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(movementMapper.toInventoryMovementResponseDto(response));
    }

    /**
     * Endpoint to get all purchases with filters and pagination
     */
    @Operation(summary = "Get all inventory movements", description = "List all inventory movements using criteria filters and pagination")
    @GetMapping
    @Override
    public ResponseEntity<GetAllInventoryMovementResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery,
                                                                     @ParameterObject GetAllInventoryMovementQueryDto queryDto) {

        GetAllInventoryMovementRequest request = movementMapper.toGetAllInventoryMovementRequest(queryDto, paginationQuery);
        GetAllInventoryMovementResponse response = mediator.dispatch(request);

        return ResponseEntity.ok(movementMapper.toGetAllInventoryMovementResponseDto(response));
    }
}