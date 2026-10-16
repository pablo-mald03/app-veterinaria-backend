package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Principal customer summary dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientSummaryDto {
    private Long id;
    private String firstName;
    private String dpi;
}