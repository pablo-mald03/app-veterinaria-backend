package com.happypets.app_veterinaria_backend.sale.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleRequest;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleResponse;
import com.happypets.app_veterinaria_backend.sale.application.query.findById.GetSaleByIdRequest;
import com.happypets.app_veterinaria_backend.sale.application.query.findById.GetSaleByIdResponse;
import com.happypets.app_veterinaria_backend.sale.application.query.getAll.GetAllSaleRequest;
import com.happypets.app_veterinaria_backend.sale.application.query.getAll.GetAllSaleResponse;
import com.happypets.app_veterinaria_backend.sale.domain.api.SaleRestController;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.GetAllSaleQueryDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.SaleCreateRequestDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.GetAllSaleResponseDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.SaleResponseDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.mapper.SaleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Sales controller
 *
 */
@RestController
@RequestMapping("/sales")
@Tag(name = "Sales", description = "Module to manage application sales")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class SaleController implements SaleRestController {

    private final Mediator mediator;
    private final SaleMapper saleMapper;

    /**
     * Endpoint to register a new sale (deducts UEPS inventory & records movements)
     */
    @Operation(summary = "Create sale", description = "Register a new sale and automatically deduct UEPS inventory batches")
    @PostMapping
    public ResponseEntity<SaleResponseDto> create(@Valid @RequestBody SaleCreateRequestDto request) {
        CreateSaleRequest appRequest = saleMapper.toCreateSaleRequest(request);
        CreateSaleResponse response = mediator.dispatch(appRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleMapper.toSaleResponseDto(response));
    }

    /**
     * Endpoint to get sale by id
     */
    @Operation(summary = "Get sale by id", description = "Fetch a single sale by its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> findById(@PathVariable Long id) {
        GetSaleByIdRequest appRequest = saleMapper.toGetSaleByIdRequest(id);
        GetSaleByIdResponse response = mediator.dispatch(appRequest);
        return ResponseEntity.ok(saleMapper.toSaleResponseDto(response));
    }

    /**
     * Endpoint to get all sales with criteria filters and pagination
     */
    @Operation(summary = "Get all sales", description = "List all sales using criteria filters and pagination")
    @GetMapping
    public ResponseEntity<GetAllSaleResponseDto> findAll(
            @ParameterObject PaginationQuery paginationQuery,
            @ParameterObject GetAllSaleQueryDto queryDto) {
        GetAllSaleRequest appRequest = saleMapper.toGetAllSaleRequest(queryDto, paginationQuery);
        GetAllSaleResponse response = mediator.dispatch(appRequest);
        return ResponseEntity.ok(saleMapper.toGetAllSaleResponseDto(response));
    }
}