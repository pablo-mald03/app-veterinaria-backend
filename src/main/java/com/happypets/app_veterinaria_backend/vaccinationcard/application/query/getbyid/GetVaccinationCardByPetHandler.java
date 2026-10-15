package com.happypets.app_veterinaria_backend.vaccinationcard.application.query.getbyid;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.exception.VaccinationCardNotFoundException;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetVaccinationCardByPetHandler {

    private final VaccinationCardRepositoryPort vaccinationCardRepositoryPort;

    public VaccinationCard execute(Long idPet) {
        return vaccinationCardRepositoryPort.findByIdPet(idPet).orElseThrow(() -> new VaccinationCardNotFoundException(idPet));
    }
}
