package com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Principal sale persistency layer entity
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
public class SaleEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, unique = true)
    private String billNumber;

    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SaleStatus status = SaleStatus.COMPLETED;

    /* One user has many sales*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /*One sale has many sale details*/
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SaleDetailEntity> details = new ArrayList<>();


    //Hook for default time
    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now(ZoneId.of("America/Guatemala"));
        }
    }
}
