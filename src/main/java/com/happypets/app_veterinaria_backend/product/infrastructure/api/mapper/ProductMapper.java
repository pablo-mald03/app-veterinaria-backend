package com.happypets.app_veterinaria_backend.product.infrastructure.api.mapper;


import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.SimpleCategoryResponseDto;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.application.command.create.CreateProductRequest;
import com.happypets.app_veterinaria_backend.product.application.command.create.CreateProductResponse;
import com.happypets.app_veterinaria_backend.product.application.command.udpate.UpdateProductRequest;
import com.happypets.app_veterinaria_backend.product.application.command.udpate.UpdateProductResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getAll.GetAllProductRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getAll.GetAllProductResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination.GetAllVaccinateRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination.GetAllVaccinateResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getDewormer.GetAllDewormerRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getDewormer.GetAllDewormerResponse;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.CreateProductRequestDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.GetAllProductQueryDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.GetAllSpecificQueryDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.UpdateProductRequestDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.*;
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
public interface ProductMapper {

    /**
     * Map the creation request dto to the request
     *
     */
    CreateProductRequest toCreateProductRequest(CreateProductRequestDto request);

    /**
     * Created response dto mapping
     *
     */
    ProductDataResponseDto toCreateProductResponse(CreateProductResponse response);


    /**
     * Map the update request dto to the request
     *
     */
    @Mapping(target = "id", source = "id")
    UpdateProductRequest toUpdateProductRequest(Long id, UpdateProductRequestDto request);

    /**
     * Updated response dto mapping
     *
     */
    ProductDataResponseDto toUpdateProductResponse(UpdateProductResponse response);


    /**
     * Method to map the request dto to the request
     *
     */
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllProductRequest toGetAllProductRequest(GetAllProductQueryDto queryDto, PaginationQuery paginationQuery);


    /**
     * Method to map the products response list to the response dto
     *
     */
    List<ProductDetailResponseDto> toProductResponseDtoList(List<ProductDetail> productDetails);

    @Mapping(source = "product", target = ".")
    @Mapping(source = "category", target = "category")
    ProductDetailResponseDto toProductDetailResponseDto(ProductDetail productDetail);

    /**
     * Method to map the categories response to the response dto
     *
     */
    SimpleCategoryResponseDto toSimpleCategoryResponseDto(Category category);

    /**
     * Mapper helper
     *
     */
    default GetAllProductResponseDto toGetAllProductsResponseDto(GetAllProductResponse response) {
        PaginationResult<ProductDetail> result = response.getProducts();
        return new GetAllProductResponseDto(
                toProductResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }


    /**
     * Method to map the request dto to the request
     *
     */
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllVaccinateRequest toGetAllVaccinationRequest(GetAllSpecificQueryDto queryDto, PaginationQuery paginationQuery);

    /**
     * Method to map a product detail to the vaccination response dto (only the product is used)
     */
    @Mapping(target = ".", source = "product")
    ProductSpecificResponseDto toSpecificResponseDto(ProductDetail productDetail);


    /**
     * Method to map the request dto to the request
     *
     */
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllDewormerRequest toGetAllDewormerRequest(GetAllSpecificQueryDto queryDto, PaginationQuery paginationQuery);

    /**
     * Method to map the products response list to the response dto
     */
    List<ProductSpecificResponseDto> toSpecificResponseDtoList(List<ProductDetail> productDetails);

    default GetAllSpecificResponseDto toGetAllSpecificResponseDto(GetAllVaccinateResponse response) {
        PaginationResult<ProductDetail> result = response.getVaccinations();
        return new GetAllSpecificResponseDto(
                toSpecificResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

    default GetAllSpecificResponseDto toGetAllSpecificResponseDto(GetAllDewormerResponse response) {
        PaginationResult<ProductDetail> result = response.getVaccinations();
        return new GetAllSpecificResponseDto(
                toSpecificResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

}
