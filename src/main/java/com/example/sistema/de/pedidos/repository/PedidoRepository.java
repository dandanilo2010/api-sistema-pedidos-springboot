package com.example.sistema.de.pedidos.repository;

import com.example.sistema.de.pedidos.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
}
