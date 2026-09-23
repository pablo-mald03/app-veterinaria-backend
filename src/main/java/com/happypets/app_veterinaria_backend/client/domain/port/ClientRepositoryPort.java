package com.happypets.app_veterinaria_backend.client.domain.port;

import com.happypets.app_veterinaria_backend.client.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {
    Client save(Client client);
    Optional<Client> findById(Long id);
    List<Client> findAll();
    Client update(Client client);
    void deleteById(Long id);
}
