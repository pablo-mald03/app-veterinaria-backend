package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.request;

import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Principal get all query dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSaleQueryDto {
    private String billNumber;
    private Long userId;
    private SaleStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTo;

    private Boolean withDetails = false;
}