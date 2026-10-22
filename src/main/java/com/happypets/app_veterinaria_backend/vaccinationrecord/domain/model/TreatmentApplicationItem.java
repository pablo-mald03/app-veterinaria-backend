package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** Lote consumido en una aplicación de tratamiento */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentApplicationItem {
    private Long productId;
    private Long batchId;
    private long quantity;
    private BigDecimal unitCost;
}