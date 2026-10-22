package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.TreatmentApplicationItem;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence.TreatmentApplicationItemEntity;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence.TreatmentApplicationItemJpaRepository;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence.VaccinationRecordEntity;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence.VaccinationRecordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VaccinationRecordRepositoryAdapter implements VaccinationRecordRepositoryPort {

    private final VaccinationRecordJpaRepository recordJpa;
    private final TreatmentApplicationItemJpaRepository itemJpa;

    @Override
    @Transactional
    public VaccinationRecord save(VaccinationRecord record) {
        VaccinationRecordEntity saved = recordJpa.save(VaccinationRecordEntity.builder()
                .idCard(record.getIdCard())
                .idTreatment(record.getIdTreatment())
                .idDoctor(record.getIdDoctor())
                .applicationNumber(record.getApplicationNumber())
                .applicationDate(record.getApplicationDate())
                .nextDoseDate(record.getNextDoseDate())
                .totalCost(record.getTotalCost())
                .notes(record.getNotes())
                .build());

        List<TreatmentApplicationItemEntity> items = record.getItems().stream()
                .map(i -> TreatmentApplicationItemEntity.builder()
                        .idApplication(saved.getIdRecord())
                        .productId(i.getProductId())
                        .batchId(i.getBatchId())
                        .quantity(i.getQuantity())
                        .unitCost(i.getUnitCost())
                        .build())
                .toList();
        itemJpa.saveAll(items);

        return toDomain(saved, items);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VaccinationRecord> findByIdCard(Long idCard) {
        return recordJpa.findByIdCardOrderByApplicationNumberAsc(idCard).stream()
                .map(e -> toDomain(e, itemJpa.findByIdApplication(e.getIdRecord())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByIdCardAndIdTreatment(Long idCard, Long idTreatment) {
        return recordJpa.countByIdCardAndIdTreatment(idCard, idTreatment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VaccinationRecord> findPending(LocalDate until) {
        return recordJpa.findPending(until).stream()
                .map(e -> toDomain(e, itemJpa.findByIdApplication(e.getIdRecord())))
                .toList();
    }

    private VaccinationRecord toDomain(VaccinationRecordEntity e, List<TreatmentApplicationItemEntity> items) {
        return VaccinationRecord.builder()
                .idRecord(e.getIdRecord())
                .idCard(e.getIdCard())
                .idTreatment(e.getIdTreatment())
                .idDoctor(e.getIdDoctor())
                .applicationNumber(e.getApplicationNumber())
                .applicationDate(e.getApplicationDate())
                .nextDoseDate(e.getNextDoseDate())
                .totalCost(e.getTotalCost())
                .notes(e.getNotes())
                .items(items.stream()
                        .map(i -> TreatmentApplicationItem.builder()
                                .productId(i.getProductId())
                                .batchId(i.getBatchId())
                                .quantity(i.getQuantity())
                                .unitCost(i.getUnitCost())
                                .build())
                        .toList())
                .build();
    }
}