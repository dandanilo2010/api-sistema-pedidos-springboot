package com.example.sistema.de.pedidos.service;

import com.example.sistema.de.pedidos.dto.PedidoDTO;
import com.example.sistema.de.pedidos.dto.PedidoItemDTO;
import com.example.sistema.de.pedidos.entity.ClienteEntity;
import com.example.sistema.de.pedidos.entity.PedidoEntity;
import com.example.sistema.de.pedidos.entity.PedidoItemEntity;
import com.example.sistema.de.pedidos.entity.ProdutoEntity;
import com.example.sistema.de.pedidos.enums.StatusPedido;
import com.example.sistema.de.pedidos.exception.BusinessException;
import com.example.sistema.de.pedidos.exception.ResourceNotFoundException;
import com.example.sistema.de.pedidos.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {

        ClienteEntity cliente = clienteService.buscarPorIdEntity(pedidoDTO.getClienteId());

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        if (pedidoDTO.getItens() == null || pedidoDTO.getItens().isEmpty()) {
            throw new BusinessException("O pedido deve conter ao menos um item");
        }

        List<PedidoItemEntity> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (PedidoItemDTO itemDTO : pedidoDTO.getItens()) {
            ProdutoEntity produto = produtoService.buscarPorIdEntity(itemDTO.getProdutoId());

            if (itemDTO.getQuantidade() == null || itemDTO.getQuantidade() <= 0) {
                throw new BusinessException("A quantidade do item deve ser maior que zero");
            }

            PedidoItemEntity item = new PedidoItemEntity();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            BigDecimal subtotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));

            item.setSubtotal(subtotal);

            itens.add(item);
            total = total.add(subtotal);
        }

        pedido.setItens(itens);
        pedido.setValorTotal(total);

        PedidoEntity salvo = pedidoRepository.save(pedido);

        return toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public Page<PedidoDTO> listarPedidos(Pageable pageable) {
        return pedidoRepository.findAll(pageable)
                .map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return toDTO(pedido);
    }

    public PedidoDTO atualizarStatus(Long id, StatusPedido novoStatus) {

        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        StatusPedido statusAtual = pedido.getStatus();

        if (statusAtual == StatusPedido.CANCELADO) {
            throw new BusinessException("Pedido já foi cancelado");
        }

        if (statusAtual == StatusPedido.ENTREGUE) {
            throw new BusinessException("Pedido já foi finalizado");
        }

        if (statusAtual == StatusPedido.PENDENTE && novoStatus != StatusPedido.PROCESSANDO) {
            throw new BusinessException("Pedido só pode ir de PENDENTE para PROCESSANDO");
        }

        if (statusAtual == StatusPedido.PROCESSANDO && novoStatus != StatusPedido.ENVIADO) {
            throw new BusinessException("Pedido só pode ir de PROCESSANDO para ENVIADO");
        }

        if (statusAtual == StatusPedido.ENVIADO && novoStatus != StatusPedido.ENTREGUE) {
            throw new BusinessException("Pedido só pode ir de ENVIADO para ENTREGUE");
        }

        pedido.setStatus(novoStatus);

        PedidoEntity atualizado = pedidoRepository.save(pedido);

        return toDTO(atualizado);
    }

    public PedidoDTO toDTO(PedidoEntity pedido) {

        List<PedidoItemDTO> itensDTO = pedido.getItens().stream().map(item -> {
            PedidoItemDTO dto = new PedidoItemDTO();
            dto.setProdutoId(item.getProduto().getId());
            dto.setNomeProduto(item.getProduto().getNome());
            dto.setQuantidade(item.getQuantidade());
            dto.setPrecoUnitario(item.getPrecoUnitario());
            dto.setSubtotal(item.getSubtotal());
            return dto;
        }).toList();

        return new PedidoDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                itensDTO,
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getStatus().name()
        );
    }
}