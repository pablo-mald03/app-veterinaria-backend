package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get all paginated purchase response dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPurchaseResponseDto {
    private List<PurchaseResponseDto> purchases;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}