package com.happypets.app_veterinaria_backend.category.domain.api;

import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.CategoryCreateRequestDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.CategoryUpdateRequest;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.ChangeStatusCategoryRequestDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.GetAllCategoryQueryDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.CategoryResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.CategoryUpdatedResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.GetAllCategoryResponseDto;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Principal category controller contest
 *
 */
public interface CategoryRestController {

    ResponseEntity<CategoryUpdatedResponseDto> create(@Valid @RequestBody CategoryCreateRequestDto request);

    ResponseEntity<CategoryUpdatedResponseDto> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request);

    ResponseEntity<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusCategoryRequestDto categoryRequestDto);

    ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id);

    ResponseEntity<GetAllCategoryResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllCategoryQueryDto queryDto);
}
