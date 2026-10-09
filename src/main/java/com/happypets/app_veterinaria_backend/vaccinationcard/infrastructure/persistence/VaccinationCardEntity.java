package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.persistence;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vaccination_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationCardEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCard;

    @Column(nullable = false, unique = true)
    private Long idPet;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private boolean status;

}
