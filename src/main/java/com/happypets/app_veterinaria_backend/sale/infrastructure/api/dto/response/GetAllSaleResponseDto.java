package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get all sale response dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllSaleResponseDto {
    private List<SaleResponseDto> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}