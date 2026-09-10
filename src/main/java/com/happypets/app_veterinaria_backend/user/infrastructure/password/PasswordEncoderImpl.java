package com.happypets.app_veterinaria_backend.user.infrastructure.password;


import com.happypets.app_veterinaria_backend.user.domain.password.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Principal password encoder port implementation
 *
 */
@Service
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
