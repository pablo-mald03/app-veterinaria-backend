package com.happypets.app_veterinaria_backend.auth.domain.port;


import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;

/**
 * Principal authenticated user port verification
 *
 */
public interface AuthenticatedUserPort {

    /**
     * Method to get the authenticated user
     *
     */
    AuthUser getAuthenticatedUser();
}
