package com.happypets.app_veterinaria_backend.sale.application.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal sale created event
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreatedEvent {
    private Long saleId;
}
