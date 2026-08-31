package com.happypets.app_veterinaria_backend.service;

import com.happypets.app_veterinaria_backend.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.entity.Client;
import com.happypets.app_veterinaria_backend.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll().stream().map(this::mapearAResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(()-> new RuntimeException("Cliente no encontrado con el ID: " + id));
        return mapearAResponseDTO(client);
    }

    @Transactional
    public ClientResponseDTO save(ClientRequestDTO requestDTO) {
        Client nuevoClient = new Client();
        nuevoClient.setDpi(requestDTO.dpi());
        nuevoClient.setFirstName(requestDTO.firstName());
        nuevoClient.setLastName(requestDTO.lastName());
        nuevoClient.setPhone(requestDTO.phone());
        nuevoClient.setAddress(requestDTO.address());
        nuevoClient.setEmail(requestDTO.email());

        Client clientSave = clientRepository.save(nuevoClient);
        return mapearAResponseDTO(clientSave);
    }

    @Transactional
    public ClientResponseDTO actualizarCliente(Long id, ClientRequestDTO requestDTO) {
        Client clientExist = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));

        clientExist.setFirstName(requestDTO.firstName());
        clientExist.setLastName(requestDTO.lastName());
        clientExist.setPhone(requestDTO.phone());
        clientExist.setAddress(requestDTO.address());
        clientExist.setEmail(requestDTO.email());

        Client clienteActualizado = clientRepository.save(clientExist);
        return mapearAResponseDTO(clienteActualizado);
    }

    private ClientResponseDTO mapearAResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getDpi(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhone(),
                client.getAddress(),
                client.getEmail()
        );
    }
}
