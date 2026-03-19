package com.example.sistema.de.pedidos.repository;
import com.example.sistema.de.pedidos.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByEmail(String email);
}
