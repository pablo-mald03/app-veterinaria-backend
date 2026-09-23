package com.happypets.app_veterinaria_backend.client.infrastructure.database.adapter;

import com.happypets.app_veterinaria_backend.client.infrastructure.database.entity.ClientJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJPARepository extends JpaRepository<ClientJPAEntity, Long> {
}
