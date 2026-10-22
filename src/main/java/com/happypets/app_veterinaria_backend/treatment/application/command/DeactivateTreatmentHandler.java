package com.happypets.app_veterinaria_backend.treatment.application.command;

import com.happypets.app_veterinaria_backend.treatment.domain.exception.TreatmentNotFoundException;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeactivateTreatmentHandler {

    private final TreatmentRepositoryPort treatmentPort;

    @Transactional
    public void execute(Long id) {
        treatmentPort.findById(id).orElseThrow(() -> new TreatmentNotFoundException(id));
        treatmentPort.deactivate(id);
    }
}