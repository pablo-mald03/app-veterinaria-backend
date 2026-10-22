package com.happypets.app_veterinaria_backend.treatment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentProduct {
    private Long productId;
    private int quantity;  /*CANTIDAD DE PRODUCTO PARA APLICAR*/
}