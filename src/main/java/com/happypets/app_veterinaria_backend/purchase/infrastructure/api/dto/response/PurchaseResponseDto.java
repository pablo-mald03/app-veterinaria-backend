package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Principal purchase response
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDto {
    private Long id;
    private String description;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal total;
    private PurchaseStatus status;
    private UserSummaryDto user;
    private List<PurchaseDetailResponseDto> details;
}