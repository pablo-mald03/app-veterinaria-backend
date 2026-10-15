package com.happypets.app_veterinaria_backend.category.infrastructure.api.mapper;


import com.happypets.app_veterinaria_backend.category.application.command.create.CreateCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.command.create.CreateCategoryResponse;
import com.happypets.app_veterinaria_backend.category.application.command.update.UpdateCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.command.update.UpdateCategoryResponse;
import com.happypets.app_veterinaria_backend.category.application.query.getAll.GetAllCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.query.getAll.GetAllCategoryResponse;
import com.happypets.app_veterinaria_backend.category.application.query.getById.GetCategoryByIdRequest;
import com.happypets.app_veterinaria_backend.category.application.query.getById.GetCategoryByIdResponse;
import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.CategoryCreateRequestDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.CategoryUpdateRequest;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.GetAllCategoryQueryDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.CategoryResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.CategoryUpdatedResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.GetAllCategoryResponseDto;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for application layer
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    /**
     * Map the creation request dto to the request
     *
     */
    CreateCategoryRequest toCreateCategoryRequest(CategoryCreateRequestDto request);

    /**
     * Map the creation response to the response dto
     *
     */
    CategoryUpdatedResponseDto toUpdateResponseDto(CreateCategoryResponse response);

    /**
     * Map the update request dto to the application request
     *
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    UpdateCategoryRequest toUpdateCategoryRequest(Long id, CategoryUpdateRequest dto);

    /**
     * Map the update response to the response dto
     *
     */
    CategoryUpdatedResponseDto toUpdateResponseDto(UpdateCategoryResponse response);

    /**
     * Method to map the request dto to the request
     *
     */
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllCategoryRequest toGetAllCategoryRequest(GetAllCategoryQueryDto queryDto, PaginationQuery paginationQuery);


    /**
     * Method to map the categories response list to the response dto
     *
     */
    List<CategoryResponseDto> toCategoryResponseDtoList(List<Category> categories);


    /**
     * Mapper helper
     *
     */
    default GetAllCategoryResponseDto toGetAllCategoryResponseDto(GetAllCategoryResponse response) {
        PaginationResult<Category> result = response.getCategories();
        return new GetAllCategoryResponseDto(
                toCategoryResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

    /**
     * Method to map the categories request param by id to the request
     *
     */
    default GetCategoryByIdRequest toGetCategoryByIdRequest(Long id) {
        return new GetCategoryByIdRequest(id);
    }

    /**
     * Method to map the categories response to the response dto
     *
     */
    CategoryResponseDto toCategoryResponseDto(GetCategoryByIdResponse response);
}
