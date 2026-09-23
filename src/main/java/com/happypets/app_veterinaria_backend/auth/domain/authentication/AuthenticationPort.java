package com.happypets.app_veterinaria_backend.auth.domain.authentication;

/**
 * Principal authentication port interface
 *
 */
public interface AuthenticationPort {

    AuthenticationResult authenticate(String email, String password);
}
