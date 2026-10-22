package com.happypets.app_veterinaria_backend.treatment.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "treatments")
@Getter
@Setter
@NoArgsConstructor
public class TreatmentEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treatment")
    private Long idTreatment;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "interval_days", nullable = false)
    private Integer intervalDays;

    @Column(nullable = false)
    private boolean status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "treatment_species", joinColumns = @JoinColumn(name = "id_treatment"))
    @Column(name = "species", nullable = false, length = 50)
    private Set<String> species = new HashSet<>();

    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TreatmentProductEntity> products = new HashSet<>();
}