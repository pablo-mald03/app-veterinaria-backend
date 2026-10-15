package com.happypets.app_veterinaria_backend.purchase.domain.entity;

import com.happypets.app_veterinaria_backend.user.domain.entity.UserSummary;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Principal purchase domain entity
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Long id;
    private String description;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal total;
    private PurchaseStatus status;
    private Long userId;
    private UserSummary user;
    private List<PurchaseDetail> details;
}