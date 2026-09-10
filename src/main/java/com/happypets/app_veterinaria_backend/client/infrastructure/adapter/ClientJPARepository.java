package com.happypets.app_veterinaria_backend.client.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.client.infrastructure.entity.ClientJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJPARepository extends JpaRepository<ClientJPAEntity, Long> {
}
