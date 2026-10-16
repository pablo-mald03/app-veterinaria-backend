package com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
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
 * Principal purchases persistency layer entity
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchases")
public class PurchaseEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime date;
    private BigDecimal total;
    private BigDecimal subtotal;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PurchaseStatus status = PurchaseStatus.COMPLETED;

    /* One user has many purchases */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /* One purchase has many details*/
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseDetailEntity> details = new ArrayList<>();

    //Hook for default time
    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now(ZoneId.of("America/Guatemala"));
        }
    }
}
