package com.happypets.app_veterinaria_backend.client.domain.port;

import com.happypets.app_veterinaria_backend.client.domain.model.Client;

import java.util.List;

public interface ClientRepositoryPort {
    Client save(Client client);
    Client findById(String id);
    List<Client> findAll();
    Client update(Client client);
    void deleteById(String id);
}
