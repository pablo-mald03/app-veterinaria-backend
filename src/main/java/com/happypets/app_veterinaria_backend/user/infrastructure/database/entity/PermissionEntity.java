package com.happypets.app_veterinaria_backend.user.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistence layer for the permissions
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "permissions")
public class PermissionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String module;
    private String action;
    private String description;


    @ManyToMany(mappedBy = "assignedPermissions")
    private Set<RoleEntity> roles = new HashSet<>();

    /**
     * Comparation method
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    /**
     * Compare hash method
     *
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
