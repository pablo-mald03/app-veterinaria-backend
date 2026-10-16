package com.happypets.app_veterinaria_backend.payment.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_items")
@Getter
@Setter
public class PaymentItemJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentJPAEntity payment;

    @Column(nullable = false)
    private String description; // Ej: "Vacuna Antirrábica", "Consulta General"

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal subtotal;

    // --- Campos de trazabilidad (Polimorfismo suave) ---
    // Nos dice de qué módulo viene (CONSULTATION, VACCINE, SUPPLY)
    @Column(name = "reference_type", nullable = false)
    private String referenceType;

    // El ID real en la tabla de vacunas o insumos (nulo si es un cargo genérico)
    @Column(name = "reference_id")
    private Long referenceId;

    // Metodo autocalculado antes de persistir
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (this.unitPrice != null && this.quantity != null) {
            this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}
