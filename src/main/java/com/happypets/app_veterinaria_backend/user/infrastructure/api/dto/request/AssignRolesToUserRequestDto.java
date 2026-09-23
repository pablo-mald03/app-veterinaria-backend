package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Assign roles to user request dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesToUserRequestDto {
    @NotEmpty(message = "Debe asignar al menos un rol al usuario")
    private Set<@NotBlank String> roleAliases;
}