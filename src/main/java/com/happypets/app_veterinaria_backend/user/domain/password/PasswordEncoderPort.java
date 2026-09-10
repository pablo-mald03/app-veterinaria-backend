package com.happypets.app_veterinaria_backend.user.domain.password;


/**
 * Principal password encoder port
 * */
public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
