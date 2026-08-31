package com.happypets.app_veterinaria_backend.repository;

import com.happypets.app_veterinaria_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
