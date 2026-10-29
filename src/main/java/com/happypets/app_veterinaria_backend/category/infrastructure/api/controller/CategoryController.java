package com.happypets.app_veterinaria_backend.category.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.category.application.command.create.CreateCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.command.create.CreateCategoryResponse;
import com.happypets.app_veterinaria_backend.category.application.command.patch.ChangeStatusCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.command.update.UpdateCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.command.update.UpdateCategoryResponse;
import com.happypets.app_veterinaria_backend.category.application.query.getAll.GetAllCategoryRequest;
import com.happypets.app_veterinaria_backend.category.application.query.getAll.GetAllCategoryResponse;
import com.happypets.app_veterinaria_backend.category.application.query.getById.GetCategoryByIdRequest;
import com.happypets.app_veterinaria_backend.category.application.query.getById.GetCategoryByIdResponse;
import com.happypets.app_veterinaria_backend.category.domain.api.CategoryRestController;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.CategoryCreateRequestDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.CategoryUpdateRequest;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.ChangeStatusCategoryRequestDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.request.GetAllCategoryQueryDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.CategoryResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.CategoryUpdatedResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.GetAllCategoryResponseDto;
import com.happypets.app_veterinaria_backend.category.infrastructure.api.mapper.CategoryMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Module to manage the application categories")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class CategoryController implements CategoryRestController {

    private final Mediator mediator;

    private final CategoryMapper categoryMapper;

    @PreAuthorize("hasAuthority('categorias:crear')")
    @Operation(summary = "Create category", description = "Create a new category")
    @PostMapping
    @Override
    public ResponseEntity<CategoryUpdatedResponseDto> create(@Valid @RequestBody CategoryCreateRequestDto request) {
        CreateCategoryRequest createRequest = categoryMapper.toCreateCategoryRequest(request);
        CreateCategoryResponse dispatch = mediator.dispatch(createRequest);
        return ResponseEntity.ok(categoryMapper.toUpdateResponseDto(dispatch));
    }

    @PreAuthorize("hasAuthority('categorias:editar')")
    @Operation(summary = "Update category", description = "Update category properties")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CategoryUpdatedResponseDto> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {

        UpdateCategoryRequest appRequest = categoryMapper.toUpdateCategoryRequest(id, request);
        UpdateCategoryResponse appResponse = mediator.dispatch(appRequest);

        return ResponseEntity.ok(categoryMapper.toUpdateResponseDto(appResponse));
    }

    @PreAuthorize("hasAuthority('categorias:eliminar')")
    @Operation(summary = "Change category status", description = "Change category status")
    @PatchMapping("/{id}/status")
    @Override
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusCategoryRequestDto categoryRequestDto) {

        ChangeStatusCategoryRequest request = new ChangeStatusCategoryRequest(id, categoryRequestDto.getStatus());
        mediator.dispatch(request);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('categorias:ver')")
    @Operation(summary = "Get category by id", description = "Get category by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id) {

        GetCategoryByIdRequest appRequest = categoryMapper.toGetCategoryByIdRequest(id);
        GetCategoryByIdResponse appResponse = mediator.dispatch(appRequest);

        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(appResponse));
    }

    @PreAuthorize("hasAuthority('categorias:ver')")
    @Operation(summary = "Get all categories", description = "List all registered categories")
    @GetMapping
    @Override
    public ResponseEntity<GetAllCategoryResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllCategoryQueryDto queryDto) {

        GetAllCategoryRequest request = categoryMapper.toGetAllCategoryRequest(queryDto, paginationQuery);
        GetAllCategoryResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(categoryMapper.toGetAllCategoryResponseDto(response));
    }
}
