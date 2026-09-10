package com.happypets.app_veterinaria_backend.client.domain.excepcions;

import com.happypets.app_veterinaria_backend.common.application.exception.ResourceNotFoundException;

public class ClientNotFoundException extends ResourceNotFoundException {
    public ClientNotFoundException(Long id) {
        super("No se encontró ningún cliente registrado con el ID: " + id);
    }
}
