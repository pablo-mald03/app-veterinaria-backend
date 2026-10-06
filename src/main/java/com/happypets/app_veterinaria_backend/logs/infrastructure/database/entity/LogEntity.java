package com.happypets.app_veterinaria_backend.logs.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Principal Log entity for persistence
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logs")
public class LogEntity extends AuditableEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70)
    private String module;
    @Column(length = 70)
    private String action;
    @Column(columnDefinition = "TEXT")
    private String detail;

    //Many to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
