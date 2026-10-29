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
import com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination.GetAllVaccinateRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination.GetAllVaccinateResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getById.GetProductByIdRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getById.GetProductByIdResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getDewormer.GetAllDewormerRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getDewormer.GetAllDewormerResponse;
import com.happypets.app_veterinaria_backend.product.application.query.getExpiringProducts.GetExpiringProductsRequest;
import com.happypets.app_veterinaria_backend.product.application.query.getExpiringProducts.GetExpiringProductsResponse;
import com.happypets.app_veterinaria_backend.product.domain.api.ProductRestController;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request.*;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.*;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.mapper.ProductMapper;
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
@RequestMapping("/products")
@Tag(name = "Products", description = "Module to manage the application products")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class ProductController implements ProductRestController {

    //Dependencies
    private final Mediator mediator;
    private final ProductMapper productMapper;


    @PreAuthorize("hasAuthority('productos:crear')")
    @Operation(summary = "Create product", description = "Create a new product")
    @PostMapping
    @Override
    public ResponseEntity<ProductDataResponseDto> create(@Valid @RequestBody CreateProductRequestDto request) {
        CreateProductRequest createRequest = productMapper.toCreateProductRequest(request);
        CreateProductResponse dispatch = mediator.dispatch(createRequest);
        return ResponseEntity.ok(productMapper.toCreateProductResponse(dispatch));
    }

    @PreAuthorize("hasAuthority('productos:editar')")
    @Operation(summary = "Update product", description = "Update product properties")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ProductDataResponseDto> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequestDto request) {

        UpdateProductRequest appRequest = productMapper.toUpdateProductRequest(id, request);
        UpdateProductResponse appResponse = mediator.dispatch(appRequest);
        return ResponseEntity.ok(productMapper.toUpdateProductResponse(appResponse));
    }

    @PreAuthorize("hasAuthority('productos:ver')")
    @Operation(summary = "Get all products", description = "List all registered products")
    @GetMapping
    @Override
    public ResponseEntity<GetAllProductResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllProductQueryDto queryDto) {

        GetAllProductRequest request = productMapper.toGetAllProductRequest(queryDto, paginationQuery);
        GetAllProductResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(productMapper.toGetAllProductsResponseDto(response));
    }


    @PreAuthorize("hasAuthority('productos:ver')")
    @Operation(summary = "Get all vaccinates", description = "List all registered vaccinates")
    @GetMapping("/vaccines")
    @Override
    public ResponseEntity<GetAllSpecificResponseDto> findAllVaccinates(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllSpecificQueryDto queryDto) {

        GetAllVaccinateRequest request = productMapper.toGetAllVaccinationRequest(queryDto, paginationQuery);
        GetAllVaccinateResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(productMapper.toGetAllSpecificResponseDto(response));
    }

    @PreAuthorize("hasAuthority('productos:ver')")
    @Operation(summary = "Get all dewormers", description = "List all registered dewormers")
    @GetMapping("/dewormers")
    @Override
    public ResponseEntity<GetAllSpecificResponseDto> findAllDewormers(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllSpecificQueryDto queryDto) {

        GetAllDewormerRequest request = productMapper.toGetAllDewormerRequest(queryDto, paginationQuery);
        GetAllDewormerResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(productMapper.toGetAllSpecificResponseDto(response));
    }


    @PreAuthorize("hasAuthority('productos:eliminar')")
    @Operation(summary = "Change product status", description = "Change product status")
    @PatchMapping("/{id}/status")
    @Override
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusProductRequestDto request) {

        ChangeStatusProductRequest appRequest = new ChangeStatusProductRequest(id, request.getStatus());
        mediator.dispatch(appRequest);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('productos:ver')")
    @Operation(summary = "Get product by id", description = "Get product detail by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ProductDetailResponseDto> findById(@PathVariable Long id) {

        GetProductByIdRequest appRequest = new GetProductByIdRequest(id);
        GetProductByIdResponse appResponse = mediator.dispatch(appRequest);

        return ResponseEntity.ok(productMapper.toProductDetailResponseDto(appResponse.getProductDetail()));
    }

    @PreAuthorize("hasAuthority('productos:ver')")
    @Operation(summary = "Get products about to expire", description = "List active products whose expiration")
    @GetMapping("/expiring")
    public ResponseEntity<GetExpiringProductsResponseDto> getExpiringProducts(
            @ParameterObject PaginationQuery paginationQuery,
            @ParameterObject @Valid GetExpiringProductsQueryDto queryDto) {

        GetExpiringProductsRequest request = productMapper.toGetExpiringProductsRequest(queryDto, paginationQuery);
        GetExpiringProductsResponse response = mediator.dispatch(request);

        return ResponseEntity.ok(productMapper.toGetExpiringProductsResponseDto(response));
    }
}
