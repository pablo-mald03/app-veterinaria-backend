package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vaccination_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecord;

    @Column(nullable = false)
    private Long idCard;

    @Column(nullable = false)
    private Long idVaccine;

    @Column(nullable = false)
    private Long idDoctor;

    @Column(nullable = false)
    private Integer doseNumber;

    @Column(nullable = false)
    private LocalDate applicationDate;

    private LocalDate nextDoseDate;

    private String batchNumber;

    @Column(length = 500)
    private String notes;
}