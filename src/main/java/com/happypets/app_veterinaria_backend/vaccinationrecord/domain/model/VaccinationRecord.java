package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model;

import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VaccinationRecord {

    private Long idRecord;
    private Long idCard;
    private Long idVaccine;
    private Long idDoctor;
    private Integer doseNumber;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String batchNumber;
    private String notes;

    public static VaccinationRecord register(Long idCard, Vaccine vaccine, Long idDoctor, int doseNumber,
                                             LocalDate applicationDate, String batchNumber, String notes) {

        return VaccinationRecord.builder().idCard(idCard).idVaccine(vaccine.getIdVaccine()).idDoctor(idDoctor).doseNumber(doseNumber)
                .applicationDate(applicationDate).nextDoseDate(calculateNextDoseDate(vaccine, doseNumber, applicationDate))
                .batchNumber(batchNumber).notes(notes).build();
    }

    private static LocalDate calculateNextDoseDate(Vaccine vaccine, int doseNumber, LocalDate applicationDate) {
        boolean hasMoreDoses = doseNumber < vaccine.getDosesRequired();
        if (hasMoreDoses && vaccine.getIntervalDays() != null) {
            return applicationDate.plusDays(vaccine.getIntervalDays());
        }
        return null;
    }
}
