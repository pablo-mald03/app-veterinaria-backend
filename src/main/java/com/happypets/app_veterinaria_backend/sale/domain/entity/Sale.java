package com.happypets.app_veterinaria_backend.sale.domain.entity;

import com.happypets.app_veterinaria_backend.user.domain.entity.UserSummary;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Principal sale domain model
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    private Long id;
    private String billNumber;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private SaleStatus status;
    private Long userId;
    private UserSummary user;

    @Builder.Default
    private List<SaleDetail> details = new ArrayList<>();
}