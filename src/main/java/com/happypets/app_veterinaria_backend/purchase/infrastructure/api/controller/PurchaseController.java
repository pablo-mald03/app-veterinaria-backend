package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.purchase.application.command.create.CreatePurchaseRequest;
import com.happypets.app_veterinaria_backend.purchase.application.command.create.CreatePurchaseResponse;
import com.happypets.app_veterinaria_backend.purchase.application.command.update.UpdatePurchaseDescriptionRequest;
import com.happypets.app_veterinaria_backend.purchase.application.query.finById.GetPurchaseByIdRequest;
import com.happypets.app_veterinaria_backend.purchase.application.query.finById.GetPurchaseByIdResponse;
import com.happypets.app_veterinaria_backend.purchase.application.query.getAll.GetAllPurchaseRequest;
import com.happypets.app_veterinaria_backend.purchase.application.query.getAll.GetAllPurchaseResponse;
import com.happypets.app_veterinaria_backend.purchase.domain.api.PurchaseRestController;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.GetAllPurchaseQueryDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.PurchaseCreateRequestDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.PurchaseUpdateDescriptionRequestDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.GetAllPurchaseResponseDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.PurchaseResponseDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.mapper.PurchaseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchases", description = "Module to manage the application purchases")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class PurchaseController implements PurchaseRestController {

    private final Mediator mediator;
    private final PurchaseMapper purchaseMapper;

    /**
     * Endpoint to register a new purchase (creates inventory batch & movement via Mediator)
     */
    @PreAuthorize("hasAuthority('compras:crear')")
    @Operation(summary = "Create purchase", description = "Register a new purchase and auto-generate UEPS inventory batch and entry movement")
    @PostMapping
    @Override
    public ResponseEntity<PurchaseResponseDto> create(@Valid @RequestBody PurchaseCreateRequestDto request) {
        CreatePurchaseRequest appRequest = purchaseMapper.toCreatePurchaseRequest(request);
        CreatePurchaseResponse response = mediator.dispatch(appRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseMapper.toPurchaseResponseDto(response));
    }

    /**
     * Endpoint to update purchase description
     */
    @PreAuthorize("hasAuthority('compras:editar')")
    @Operation(summary = "Update purchase description", description = "Update the description of an existing purchase")
    @PatchMapping("/{id}/description")
    @Override
    public ResponseEntity<Void> updateDescription(@PathVariable Long id, @Valid @RequestBody PurchaseUpdateDescriptionRequestDto request) {
        UpdatePurchaseDescriptionRequest appRequest = purchaseMapper.toUpdatePurchaseDescriptionRequest(id, request);
        mediator.dispatch(appRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to get purchase by id
     */
    @PreAuthorize("hasAuthority('compras:ver')")
    @Operation(summary = "Get purchase by id", description = "Fetch a single purchase by its unique ID")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PurchaseResponseDto> findById(@PathVariable Long id) {
        GetPurchaseByIdRequest appRequest = purchaseMapper.toGetPurchaseByIdRequest(id);
        GetPurchaseByIdResponse response = mediator.dispatch(appRequest);
        return ResponseEntity.ok(purchaseMapper.toPurchaseResponseDto(response));
    }

    /**
     * Endpoint to get all purchases with filters and pagination
     */
    @PreAuthorize("hasAuthority('compras:ver')")
    @Operation(summary = "Get all purchases", description = "List all purchases using criteria filters and pagination")
    @GetMapping
    @Override
    public ResponseEntity<GetAllPurchaseResponseDto> findAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllPurchaseQueryDto queryDto) {
        GetAllPurchaseRequest appRequest = purchaseMapper.toGetAllPurchaseRequest(queryDto, paginationQuery);
        GetAllPurchaseResponse response = mediator.dispatch(appRequest);
        return ResponseEntity.ok(purchaseMapper.toGetAllPurchaseResponseDto(response));
    }
}