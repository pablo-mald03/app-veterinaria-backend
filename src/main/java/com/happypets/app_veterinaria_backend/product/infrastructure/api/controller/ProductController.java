package com.happypets.app_veterinaria_backend.product.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.product.application.command.create.CreateProductRequest;
import com.happypets.app_veterinaria_backend.product.application.command.create.CreateProductResponse;
import com.happypets.app_veterinaria_backend.product.application.command.patch.ChangeStatusProductRequest;
import com.happypets.app_veterinaria_backend.product.application.command.udpate.UpdateProductRequest;
import com.happypets.app_veterinaria_backend.product.application.command.udpate.UpdateProductResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getAll.GetAllProductRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getAll.GetAllProductResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getById.GetProductByIdRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getById.GetProductByIdResponse;
import com.happypets.app_veterinaria_backend.product.domain.api.ProductRestController;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.ChangeStatusProductRequestDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.CreateProductRequestDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.GetAllProductQueryDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.UpdateProductRequestDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.GetAllProductResponseDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductDataResponseDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductDetailResponseDto;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Module to manage the application products")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class ProductController implements ProductRestController {

    //Dependencies
    private final Mediator mediator;
    private final ProductMapper productMapper;


    @Operation(summary = "Create product", description = "Create a new product")
    @PostMapping
    @Override
    public ResponseEntity<ProductDataResponseDto> create(@Valid @RequestBody CreateProductRequestDto request) {
        CreateProductRequest createRequest = productMapper.toCreateProductRequest(request);
        CreateProductResponse dispatch = mediator.dispatch(createRequest);
        return ResponseEntity.ok(productMapper.toCreateProductResponse(dispatch));
    }

    @Operation(summary = "Update product", description = "Update product properties")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ProductDataResponseDto> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequestDto request) {

        UpdateProductRequest appRequest = productMapper.toUpdateProductRequest(id, request);
        UpdateProductResponse appResponse = mediator.dispatch(appRequest);
        return ResponseEntity.ok(productMapper.toUpdateProductResponse(appResponse));
    }

    @Operation(summary = "Get all products", description = "List all registered products")
    @GetMapping
    @Override
    public ResponseEntity<GetAllProductResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllProductQueryDto queryDto) {

        GetAllProductRequest request = productMapper.toGetAllProductRequest(queryDto, paginationQuery);
        GetAllProductResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(productMapper.toGetAllProductsResponseDto(response));
    }


 /*   @Operation(summary = "Get all vaccinates", description = "List all registered vaccinates")
    @GetMapping
    @Override
    public ResponseEntity<GetAllProductResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllProductQueryDto queryDto) {

        GetAllVaccinateRequest request = productMapper.toGetAllProductRequest(queryDto, paginationQuery);
        GetAllVaccinateResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(productMapper.toGetAllProductsResponseDto(response));
    }*/


    @Operation(summary = "Change product status", description = "Change product status")
    @PatchMapping("/{id}/status")
    @Override
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusProductRequestDto request) {

        ChangeStatusProductRequest appRequest = new ChangeStatusProductRequest(id, request.getStatus());
        mediator.dispatch(appRequest);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get product by id", description = "Get product detail by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ProductDetailResponseDto> findById(@PathVariable Long id) {

        GetProductByIdRequest appRequest = new GetProductByIdRequest(id);
        GetProductByIdResponse appResponse = mediator.dispatch(appRequest);

        return ResponseEntity.ok(productMapper.toProductDetailResponseDto(appResponse.getProductDetail()));
    }
}
