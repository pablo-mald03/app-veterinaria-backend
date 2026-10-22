package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model;

import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VaccinationRecord {

    private Long idRecord;
    private Long idCard;
    private Long idTreatment;
    private Long idDoctor;
    private Integer applicationNumber;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private BigDecimal totalCost;
    private String notes;

    @Builder.Default
    private List<TreatmentApplicationItem> items = new ArrayList<>();

    public static VaccinationRecord register(Long idCard, Treatment treatment, Long idDoctor,
                                             int applicationNumber, LocalDate applicationDate,
                                             String notes, BigDecimal totalCost,
                                             List<TreatmentApplicationItem> items) {
        return VaccinationRecord.builder()
                .idCard(idCard)
                .idTreatment(treatment.getIdTreatment())
                .idDoctor(idDoctor)
                .applicationNumber(applicationNumber)
                .applicationDate(applicationDate)
                .nextDoseDate(nextDate(treatment, applicationDate))
                .totalCost(totalCost)
                .notes(notes)
                .items(items)
                .build();
    }

    private static LocalDate nextDate(Treatment treatment, LocalDate applicationDate) {
        return treatment.isRecurring() ? applicationDate.plusDays(treatment.getIntervalDays()) : null;
    }
}