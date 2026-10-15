package com.happypets.app_veterinaria_backend.payment.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la cita que originó esta cuenta
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private AppointmentJPAEntity appointment;

    // El monto total se irá recalculando conforme se agreguen ítems
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "payment_method")
    private String paymentMethod; // CASH, CREDIT_CARD, etc. (Puede ser nulo hasta que se pague)

    @Column(nullable = false)
    private String status; // PENDING, PAID, CANCELLED

    @Column(name = "payment_date")
    private LocalDateTime paymentDate; // Cuándo se efectuó el pago realmente

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Cuándo se abrió la cuenta

    // Relacion bidireccional con los ítems (CascadeType.ALL permite guardar el pago y sus ítems de un solo golpe)
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentItemJPAEntity> items = new ArrayList<>();

    // Metodo de conveniencia para mantener la sincronización bidireccional
    public void addItem(PaymentItemJPAEntity item) {
        items.add(item);
        item.setPayment(this);
        // Sumar al total automáticamente
        this.totalAmount = this.totalAmount.add(item.getSubtotal());
    }
}
