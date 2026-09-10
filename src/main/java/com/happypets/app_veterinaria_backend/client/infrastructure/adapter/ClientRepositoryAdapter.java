package com.happypets.app_veterinaria_backend.client.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import com.happypets.app_veterinaria_backend.client.domain.port.ClientRepositoryPort;
import com.happypets.app_veterinaria_backend.client.infrastructure.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepositoryPort {
    private final ClientJPARepository clientJPARepository;
    private final ClientMapper clientMapper;

    @Override
    public Client save(Client client){
        var entity = clientMapper.toEntity(client);
        var savedEntity = clientJPARepository.save(entity);
        return clientMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Client> findById(Long id){
        return clientJPARepository.findById(id).map(clientMapper::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return clientJPARepository.findAll().stream()
                .map(clientMapper::toDomain).toList();
    }

    @Override
    public Client update(Client client) {
        var entity = clientMapper.toEntity(client);
        var savedEntity = clientJPARepository.save(entity);
        return clientMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        clientJPARepository.deleteById(id);
    }


}
