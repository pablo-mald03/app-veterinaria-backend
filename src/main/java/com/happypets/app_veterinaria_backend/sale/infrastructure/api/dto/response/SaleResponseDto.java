package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Principal sale response dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponseDto {
    private Long id;
    private String billNumber;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private SaleStatus status;
    private UserSummaryDto user;
    private List<SaleDetailResponseDto> details;
}