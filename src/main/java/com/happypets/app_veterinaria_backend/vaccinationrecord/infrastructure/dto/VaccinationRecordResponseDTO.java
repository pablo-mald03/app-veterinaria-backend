package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record VaccinationRecordResponseDTO(
        Long idRecord,
        Long idCard,
        Long idTreatment,
        Long idDoctor,
        Integer applicationNumber,
        LocalDate applicationDate,
        LocalDate nextDoseDate,
        BigDecimal totalCost,
        String notes,
        List<ItemDTO> items
) {
    public record ItemDTO(Long productId, Long batchId, long quantity, BigDecimal unitCost) {}

    public static VaccinationRecordResponseDTO from(VaccinationRecord r) {
        return new VaccinationRecordResponseDTO(
                r.getIdRecord(), r.getIdCard(), r.getIdTreatment(), r.getIdDoctor(),
                r.getApplicationNumber(), r.getApplicationDate(), r.getNextDoseDate(),
                r.getTotalCost(), r.getNotes(),
                r.getItems().stream()
                        .map(i -> new ItemDTO(i.getProductId(), i.getBatchId(), i.getQuantity(), i.getUnitCost()))
                        .toList());
    }
}