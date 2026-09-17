package com.happypets.app_veterinaria_backend.user.application.command.assignRole;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * Principal update user roles request class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesToUserRequest implements Request<AssignRolesToUserResponse> {
    private Long userId;
    private Set<String> roleAliases;
}