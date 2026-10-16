package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll.GetAllInventoryMovementRequest;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll.GetAllInventoryMovementResponse;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getById.GetInventoryMovementByIdRequest;
import com.happypets.app_veterinaria_backend.inventoryMovement.application.getById.GetInventoryMovementByIdResponse;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.request.GetAllInventoryMovementQueryDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response.GetAllInventoryMovementResponseDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response.InventoryMovementResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InventoryMovementMapper {

    GetInventoryMovementByIdRequest toGetInventoryMovementByIdRequest(Long id);

    default InventoryMovementResponseDto toInventoryMovementResponseDto(GetInventoryMovementByIdResponse response) {
        return toInventoryMovementResponseDto(response.getMovement());
    }

    InventoryMovementResponseDto toInventoryMovementResponseDto(InventoryMovement movement);

    @Mapping(target = "productId", source = "queryDto.productId")
    @Mapping(target = "batchId", source = "queryDto.batchId")
    @Mapping(target = "purchaseId", source = "queryDto.purchaseId")
    @Mapping(target = "saleId", source = "queryDto.saleId")
    @Mapping(target = "type", source = "queryDto.type")
    @Mapping(target = "dateFrom", source = "queryDto.dateFrom")
    @Mapping(target = "dateTo", source = "queryDto.dateTo")
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllInventoryMovementRequest toGetAllInventoryMovementRequest(GetAllInventoryMovementQueryDto queryDto, PaginationQuery paginationQuery);

    List<InventoryMovementResponseDto> toInventoryMovementResponseDtoList(List<InventoryMovement> movements);

    default GetAllInventoryMovementResponseDto toGetAllInventoryMovementResponseDto(GetAllInventoryMovementResponse response) {
        PaginationResult<InventoryMovement> result = response.getMovements();
        return new GetAllInventoryMovementResponseDto(
                toInventoryMovementResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }
}