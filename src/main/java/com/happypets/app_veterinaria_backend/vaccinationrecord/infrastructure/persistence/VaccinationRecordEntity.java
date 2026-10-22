package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "treatment_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application")
    private Long idRecord;

    @Column(name = "id_card", nullable = false)
    private Long idCard;

    @Column(name = "id_treatment", nullable = false)
    private Long idTreatment;

    @Column(name = "id_doctor", nullable = false)
    private Long idDoctor;

    @Column(name = "application_number", nullable = false)
    private Integer applicationNumber;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "next_dose_date")
    private LocalDate nextDoseDate;

    @Column(name = "total_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalCost;

    @Column(length = 500)
    private String notes;
}