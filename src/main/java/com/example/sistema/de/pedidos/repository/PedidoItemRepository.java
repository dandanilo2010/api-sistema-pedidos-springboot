package com.example.sistema.de.pedidos.repository;

import com.example.sistema.de.pedidos.entity.PedidoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItemEntity, Long> {

    boolean existsByProdutoId(Long produtoId);
}
