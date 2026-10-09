package com.happypets.app_veterinaria_backend.vaccination.infrastructure.persistence;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Entity
@Table(name = "vaccine")
public class VaccineEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaccine")
    private Long idVaccine;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "species", nullable = false, length = 150)
    private String species;

    @Column(name = "doses_required", nullable = false)
    private Integer dosesRequired;

    @Column(name = "interval_days", nullable = false)
    private Integer intervalDays;

    @Column(name = "status", nullable = false)
    private boolean status;

}
