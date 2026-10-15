package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.request;

import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Principal get all purchase query dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPurchaseQueryDto {
    private UUID userId;
    private PurchaseStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTo;

    private Boolean withDetails = false;
}