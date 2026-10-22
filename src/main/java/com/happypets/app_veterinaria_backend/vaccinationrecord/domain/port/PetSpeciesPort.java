package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port;

import java.util.Optional;

public interface PetSpeciesPort {
    Optional<String> findSpeciesByPetId(Long idPet);
}