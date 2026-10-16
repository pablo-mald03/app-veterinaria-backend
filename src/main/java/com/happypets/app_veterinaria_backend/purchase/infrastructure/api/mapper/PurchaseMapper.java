package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.mapper;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.purchase.application.command.create.CreatePurchaseRequest;
import com.happypets.app_veterinaria_backend.purchase.application.command.create.CreatePurchaseResponse;
import com.happypets.app_veterinaria_backend.purchase.application.command.update.UpdatePurchaseDescriptionRequest;
import com.happypets.app_veterinaria_backend.purchase.application.query.finById.GetPurchaseByIdRequest;
import com.happypets.app_veterinaria_backend.purchase.application.query.finById.GetPurchaseByIdResponse;
import com.happypets.app_veterinaria_backend.purchase.application.query.getAll.GetAllPurchaseRequest;
import com.happypets.app_veterinaria_backend.purchase.application.query.getAll.GetAllPurchaseResponse;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.GetAllPurchaseQueryDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.PurchaseCreateRequestDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.PurchaseUpdateDescriptionRequestDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.GetAllPurchaseResponseDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.PurchaseResponseDto;
import com.happypets.app_veterinaria_backend.user.domain.entity.UserSummary;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.UserSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Principal mapper class for the purchase persistence and domain layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseMapper {

    /**
     * Mapper from create request dto to create request
     *
     */
    CreatePurchaseRequest toCreatePurchaseRequest(PurchaseCreateRequestDto dto);

    /**
     * Mapper from create response to create response dto
     *
     */
    default PurchaseResponseDto toPurchaseResponseDto(CreatePurchaseResponse response) {
        return toPurchaseResponseDto(response.getPurchase());
    }

    /**
     * Mapper from update request dto to update request
     *
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "dto.description")
    UpdatePurchaseDescriptionRequest toUpdatePurchaseDescriptionRequest(Long id, PurchaseUpdateDescriptionRequestDto dto);

    /**
     * Mapper from find by id request dto to find by id request
     *
     */
    GetPurchaseByIdRequest toGetPurchaseByIdRequest(Long id);

    /**
     * Mapper from find by id purchase response to response dto
     *
     */
    default PurchaseResponseDto toPurchaseResponseDto(GetPurchaseByIdResponse response) {
        return toPurchaseResponseDto(response.getPurchase());
    }

    /**
     * Mapper to get the purchase response dto from purchase domain
     *
     */
    PurchaseResponseDto toPurchaseResponseDto(Purchase purchase);

    /**
     * Mapper from find all purchase request dto to request
     *
     */
    @Mapping(target = "userId", source = "queryDto.userId")
    @Mapping(target = "status", source = "queryDto.status")
    @Mapping(target = "dateFrom", source = "queryDto.dateFrom")
    @Mapping(target = "dateTo", source = "queryDto.dateTo")
    @Mapping(target = "withDetails", source = "queryDto.withDetails")
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllPurchaseRequest toGetAllPurchaseRequest(GetAllPurchaseQueryDto queryDto, PaginationQuery paginationQuery);

    UserSummaryDto toUserSummaryDto(UserSummary domain);

    /**
     * Mapper from find by all purchase response to response dto
     *
     */
    List<PurchaseResponseDto> toPurchaseResponseDtoList(List<Purchase> purchases);

    /**
     * Mapper from find by all List purchase response to response list dto
     *
     */
    default GetAllPurchaseResponseDto toGetAllPurchaseResponseDto(GetAllPurchaseResponse response) {
        PaginationResult<Purchase> result = response.getPurchases();
        return new GetAllPurchaseResponseDto(
                toPurchaseResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }
}