package com.example.sistema.de.pedidos.service;

import com.example.sistema.de.pedidos.dto.PedidoDTO;
import com.example.sistema.de.pedidos.entity.ClienteEntity;
import com.example.sistema.de.pedidos.entity.PedidoEntity;
import com.example.sistema.de.pedidos.entity.ProdutoEntity;
import com.example.sistema.de.pedidos.exception.ResourceNotFoundException;
import com.example.sistema.de.pedidos.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteService clienteService,
                         ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    // Criar Pedido
    public PedidoDTO criarPedido(PedidoEntity pedido){

        ClienteEntity cliente = clienteService.buscarPorIdEntity(pedido.getCliente().getId());

        List<ProdutoEntity> produtos = pedido.getProdutos().stream()
                .map(p -> produtoService.buscarPorIdEntity(p.getId()))
                .collect(Collectors.toList());

        BigDecimal total = produtos.stream()
                .map(ProdutoEntity::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setCliente(cliente);
        pedido.setProdutos(produtos);
        pedido.setValorTotal(total);
        pedido.setDataPedido(LocalDateTime.now());

        PedidoEntity salvo = pedidoRepository.save(pedido);

        return toDTO(salvo);
    }


    @Transactional(readOnly = true)
    public Page<PedidoDTO> listarPedidos(Pageable pageable) {
        return pedidoRepository.findAll(pageable)
                .map(this::toDTO);  // converte cada entity para DTO
    }

    // Buscar por ID
    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return toDTO(pedido);
    }

    // Converter Entity -> DTO
    public PedidoDTO toDTO(PedidoEntity pedido){
        List<String> nomesProdutos = pedido.getProdutos()
                .stream()
                .map(ProdutoEntity::getNome)
                .collect(Collectors.toList());

        return new PedidoDTO(
                pedido.getId(),
                pedido.getCliente().getNome(),
                nomesProdutos,
                pedido.getValorTotal(),
                pedido.getDataPedido()
        );
    }
}