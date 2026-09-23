package com.happypets.app_veterinaria_backend.role.infrastructure.database.service;

import com.happypets.app_veterinaria_backend.role.domain.exeptions.InsufficientPrivilegeException;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.ReservedRoleAliasException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Principal service to valid the roles
 *
 */
@Service
public class RoleAssignmentPolicyService {

    private final String roleAlias;
    private final Set<String> reservedAliases;

    public RoleAssignmentPolicyService(@Value("${reserved.super-role}") String roleAlias) {
        this.roleAlias = roleAlias;
        this.reservedAliases = Set.of(roleAlias);
    }

    /**
     * Principal method to check the role assignments
     */
    public void validate(Set<String> requesterRoleAliases, Set<String> requestedRoleAliases) {

        Set<String> reservedRequested = requestedRoleAliases.stream()
                .filter(this.reservedAliases::contains)
                .collect(Collectors.toSet());

        if (!reservedRequested.isEmpty()) {
            throw new ReservedRoleAliasException("Los roles " + reservedRequested + " no se pueden asignar por API");
        }

        if (requesterRoleAliases.contains(this.roleAlias)) {
            return;
        }

        Set<String> notOwned = requestedRoleAliases.stream()
                .filter(alias -> !requesterRoleAliases.contains(alias))
                .collect(Collectors.toSet());

        if (!notOwned.isEmpty()) {
            throw new InsufficientPrivilegeException("No puedes otorgar roles que no posees: " + notOwned);
        }
    }

    /**
     * Principal method to check if the role has already reserved
     */
    public void validateNotReserved(String alias, String context) {
        if (this.reservedAliases.contains(alias)) {
            throw new ReservedRoleAliasException("El alias '" + alias + "' está reservado. " + context);
        }
    }

    /**
     * Principal method to check if the role is reserved
     */
    public boolean isReserved(String alias) {
        return this.reservedAliases.contains(alias);
    }

    /**
     * Normalization of the role alias
     */
    public String normalizeAlias(String alias) {
        if (alias == null) {
            return null;
        }
        return alias.trim()
                .replaceAll("\\s+", "_")
                .toUpperCase();
    }


}
