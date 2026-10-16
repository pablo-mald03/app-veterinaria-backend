package com.happypets.app_veterinaria_backend.purchase.application.command.create;

import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal create purchase response class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseResponse {

    private Purchase purchase;
}