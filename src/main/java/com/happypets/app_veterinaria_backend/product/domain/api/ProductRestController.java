package com.happypets.app_veterinaria_backend.product.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.*;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.GetAllProductResponseDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.GetAllSpecificResponseDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductDataResponseDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductDetailResponseDto;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Product controller port
 *
 */
public interface ProductRestController {

    ResponseEntity<ProductDataResponseDto> create(@Valid @RequestBody CreateProductRequestDto request);

    ResponseEntity<ProductDataResponseDto> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequestDto request);

    ResponseEntity<GetAllProductResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllProductQueryDto queryDto);

    ResponseEntity<GetAllSpecificResponseDto> findAllVaccinates(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllSpecificQueryDto queryDto);

    ResponseEntity<GetAllSpecificResponseDto> findAllDewormers(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllSpecificQueryDto queryDto);

    ResponseEntity<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusProductRequestDto request);

    ResponseEntity<ProductDetailResponseDto> findById(@PathVariable Long id);
}
