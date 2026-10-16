package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.mapper;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll.GetAllInventoryBatchRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll.GetAllInventoryBatchResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findById.GetInventoryBatchByIdRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.findById.GetInventoryBatchByIdResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getLow.GetLowStockProductsResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary.GetAllProductStockSummaryRequest;
import com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary.GetAllProductStockSummaryResponse;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.request.GetAllInventoryBatchQueryDto;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for inventory batch application layer
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InventoryBatchMapper {

    /**
     * Principal request get by id dto mapper
     *
     */
    GetInventoryBatchByIdRequest toGetInventoryBatchByIdRequest(Long id);

    /**
     * Principal response get by id dto mapper
     *
     */
    default InventoryBatchResponseDto toInventoryBatchResponseDto(GetInventoryBatchByIdResponse response) {
        return toInventoryBatchResponseDto(response.getBatch());
    }

    /**
     * Principal response get by id dto mapper
     *
     */
    InventoryBatchResponseDto toInventoryBatchResponseDto(InventoryBatch batch);

    /**
     * Principal request get all dto mapper
     *
     */
    @Mapping(target = "productId", source = "queryDto.productId")
    @Mapping(target = "status", source = "queryDto.status")
    @Mapping(target = "onlyAvailable", source = "queryDto.onlyAvailable")
    @Mapping(target = "dateFrom", source = "queryDto.dateFrom")
    @Mapping(target = "dateTo", source = "queryDto.dateTo")
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllInventoryBatchRequest toGetAllInventoryBatchRequest(GetAllInventoryBatchQueryDto queryDto, PaginationQuery paginationQuery);

    /**
     * Principal response get all dto list mapper
     *
     */
    List<InventoryBatchResponseDto> toInventoryBatchResponseDtoList(List<InventoryBatch> batches);

    /**
     * Principal response get all dto mapper
     *
     */
    default GetAllInventoryBatchResponseDto toGetAllInventoryBatchResponseDto(GetAllInventoryBatchResponse response) {
        PaginationResult<InventoryBatch> result = response.getBatches();
        List<InventoryBatchResponseDto> content = toInventoryBatchResponseDtoList(result.getContent());

        return new GetAllInventoryBatchResponseDto(
                content,
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

    /**
     * Principal response get all dto list mapper
     *
     */
    List<LowStockProductSummaryResponseDto> toGetLowStockProductsResponseDtoList(List<LowStockProductSummary> lowStocks);


    /**
     * Principal response get all dto mapper
     *
     */
    default GetLowStockProductsResponseDto toGetLowStockListProductsResponseDto(GetLowStockProductsResponse response) {
        PaginationResult<LowStockProductSummary> result = response.getContent();
        List<LowStockProductSummaryResponseDto> content = toGetLowStockProductsResponseDtoList(result.getContent());
        return new GetLowStockProductsResponseDto(
                content,
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

    /**
     * Principal request get all product stock summary mapper
     *
     */
    default GetAllProductStockSummaryRequest toGetAllProductStockSummaryRequest(PaginationQuery paginationQuery) {
        return new GetAllProductStockSummaryRequest(paginationQuery);
    }

    /**
     * Principal response product stock summary dto mapper
     *
     */
    ProductStockSummaryResponseDto toProductStockSummaryResponseDto(ProductStockSummary summary);

    /**
     * Principal response product stock summary dto list mapper
     *
     */
    List<ProductStockSummaryResponseDto> toProductStockSummaryResponseDtoList(List<ProductStockSummary> summaries);

    /**
     * Principal response get all product stock summary dto mapper
     *
     */
    default GetAllProductStockSummaryResponseDto toGetAllProductStockSummaryResponseDto(GetAllProductStockSummaryResponse response) {
        PaginationResult<ProductStockSummary> result = response.getSummaries();
        List<ProductStockSummaryResponseDto> content = toProductStockSummaryResponseDtoList(result.getContent());

        return new GetAllProductStockSummaryResponseDto(
                content,
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }
}