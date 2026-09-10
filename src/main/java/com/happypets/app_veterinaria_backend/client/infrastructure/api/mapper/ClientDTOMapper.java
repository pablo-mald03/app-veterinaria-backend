package com.happypets.app_veterinaria_backend.client.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientRequestDTO;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientDTOMapper {
    @Mapping(target = "id", ignore = true)
    Client toDomain(ClientRequestDTO dto);
    ClientResponseDTO toDTO(Client client);
}
