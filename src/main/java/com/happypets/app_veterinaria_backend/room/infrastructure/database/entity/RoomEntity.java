package com.happypets.app_veterinaria_backend.room.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 150)
    private String location;

    private String description;

    private int number;

    private boolean status = true;
}
