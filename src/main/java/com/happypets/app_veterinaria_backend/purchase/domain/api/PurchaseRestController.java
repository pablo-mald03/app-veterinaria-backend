package com.happypets.app_veterinaria_backend.purchase.domain.api;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.GetAllPurchaseQueryDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.PurchaseCreateRequestDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request.PurchaseUpdateDescriptionRequestDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.GetAllPurchaseResponseDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.PurchaseResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * Principal purchase controller port
 *
 */
public interface PurchaseRestController {

    ResponseEntity<PurchaseResponseDto> create(PurchaseCreateRequestDto request);

    ResponseEntity<Void> updateDescription(Long id, PurchaseUpdateDescriptionRequestDto request);

    ResponseEntity<PurchaseResponseDto> findById(Long id);

    ResponseEntity<GetAllPurchaseResponseDto> findAll(PaginationQuery paginationQuery, GetAllPurchaseQueryDto queryDto);
}