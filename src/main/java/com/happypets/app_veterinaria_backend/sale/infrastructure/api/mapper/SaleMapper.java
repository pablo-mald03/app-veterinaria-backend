package com.happypets.app_veterinaria_backend.sale.infrastructure.api.mapper;


import com.happypets.app_veterinaria_backend.client.domain.model.ClientSummary;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductSummaryDto;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleItemRequest;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleRequest;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleResponse;
import com.happypets.app_veterinaria_backend.sale.application.query.findById.GetSaleByIdRequest;
import com.happypets.app_veterinaria_backend.sale.application.query.findById.GetSaleByIdResponse;
import com.happypets.app_veterinaria_backend.sale.application.query.getAll.GetAllSaleRequest;
import com.happypets.app_veterinaria_backend.sale.application.query.getAll.GetAllSaleResponse;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.GetAllSaleQueryDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.SaleCreateRequestDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.SaleItemCreateRequestDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.ClientSummaryDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.GetAllSaleResponseDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.SaleResponseDto;
import com.happypets.app_veterinaria_backend.user.domain.entity.UserSummary;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.UserSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SaleMapper {

    /**
     * Principal create sale item request from dto to application layer
     *
     */
    CreateSaleItemRequest toCreateSaleItemRequest(SaleItemCreateRequestDto dto);

    /**
     * Principal create sale request from dto to application layer
     *
     */
    CreateSaleRequest toCreateSaleRequest(SaleCreateRequestDto dto);

    /**
     * Principal create sale response application layer to dto response
     *
     */
    default SaleResponseDto toSaleResponseDto(CreateSaleResponse response) {
        return toSaleResponseDto(response.getSale());
    }

    /**
     * Mapper de FindById ID a Request
     */
    GetSaleByIdRequest toGetSaleByIdRequest(Long id);

    /**
     * Mapper de FindById Response a Response DTO
     */
    default SaleResponseDto toSaleResponseDto(GetSaleByIdResponse response) {
        return toSaleResponseDto(response.getSale());
    }

    /**
     * Mapper from domain to dto
     */
    SaleResponseDto toSaleResponseDto(Sale sale);

    ClientSummaryDto toClientSummaryDto(ClientSummary domain);

    UserSummaryDto toUserSummaryDto(UserSummary domain);

    ProductSummaryDto toProductSummaryDto(ProductSummary domain);

    /**
     * Mapper de Query DTO a GetAll Request
     */
    @Mapping(target = "billNumber", source = "queryDto.billNumber")
    @Mapping(target = "userId", source = "queryDto.userId")
    @Mapping(target = "status", source = "queryDto.status")
    @Mapping(target = "dateFrom", source = "queryDto.dateFrom")
    @Mapping(target = "dateTo", source = "queryDto.dateTo")
    @Mapping(target = "withDetails", source = "queryDto.withDetails")
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllSaleRequest toGetAllSaleRequest(GetAllSaleQueryDto queryDto, PaginationQuery paginationQuery);

    /**
     * Mapper from list domain to list dto response
     */
    List<SaleResponseDto> toSaleResponseDtoList(List<Sale> sales);

    /**
     * Mapper de GetAll Response a Response List DTO con paginación
     */
    default GetAllSaleResponseDto toGetAllSaleResponseDto(GetAllSaleResponse response) {
        PaginationResult<Sale> result = response.getSales();
        return new GetAllSaleResponseDto(
                toSaleResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }
}