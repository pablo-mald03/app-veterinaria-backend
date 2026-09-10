package com.happypets.app_veterinaria_backend.client.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.client.domain.model.Client;
import com.happypets.app_veterinaria_backend.client.infrastructure.entity.ClientJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientJPAEntity toEntity(Client client);
    Client toDomain(ClientJPAEntity entity);
}
