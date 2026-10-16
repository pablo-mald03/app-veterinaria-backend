package com.happypets.app_veterinaria_backend.sale.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sale status
 *
 */
@Getter
@RequiredArgsConstructor
public enum SaleStatus {

    COMPLETED("COMPLETADA"),
    CANCELLED("CANCELADA");

    private final String description;
}