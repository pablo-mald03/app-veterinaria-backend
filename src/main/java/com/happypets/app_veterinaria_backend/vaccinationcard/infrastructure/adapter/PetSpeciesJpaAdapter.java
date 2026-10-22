package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.PetSpeciesPort;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PetSpeciesJpaAdapter implements PetSpeciesPort {

    private final EntityManager entityManager;

    @Override
    public Optional<String> findSpeciesByPetId(Long idPet) {
        // Ajusta "PetEntity" si el @Entity de mascotas tiene otro nombre
        List<String> species = entityManager
                .createQuery("SELECT p.species FROM PetEntity p WHERE p.idPet = :id", String.class)
                .setParameter("id", idPet)
                .getResultList();
        return species.stream().findFirst();
    }
}