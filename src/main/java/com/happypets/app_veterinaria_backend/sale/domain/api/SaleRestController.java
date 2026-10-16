package com.happypets.app_veterinaria_backend.sale.domain.api;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.GetAllSaleQueryDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request.SaleCreateRequestDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.GetAllSaleResponseDto;
import com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response.SaleResponseDto;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Principal sale rest controller contest
 *
 */
public interface SaleRestController {

    ResponseEntity<SaleResponseDto> findById(@PathVariable Long id);

    ResponseEntity<GetAllSaleResponseDto> findAll(
            @ParameterObject PaginationQuery paginationQuery,
            @ParameterObject GetAllSaleQueryDto queryDto);

    ResponseEntity<SaleResponseDto> create(@Valid @RequestBody SaleCreateRequestDto request);
}
