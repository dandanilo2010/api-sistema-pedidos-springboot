package com.example.sistema.de.pedidos.repository;

import com.example.sistema.de.pedidos.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
