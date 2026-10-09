package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.database.VaccinationRecordEntityMapper;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence.VaccinationRecordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VaccinationRecordRepositoryAdapter implements VaccinationRecordRepositoryPort {

    private final VaccinationRecordJpaRepository jpaRepository;
    private final VaccinationRecordEntityMapper entityMapper;

    @Override
    public VaccinationRecord save(VaccinationRecord record) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(record)));
    }

    @Override
    public List<VaccinationRecord> findByIdCard(Long idCard) {
        return jpaRepository.findByIdCardOrderByApplicationDateAsc(idCard).stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public int countByIdCardAndIdVaccine(Long idCard, Long idVaccine) {
        return (int) jpaRepository.countByIdCardAndIdVaccine(idCard, idVaccine);
    }

    @Override
    public List<VaccinationRecord> findPending(LocalDate until) {
        return jpaRepository.findPending(until).stream()
                .map(entityMapper::toDomain)
                .toList();
    }
}