package com.example.sistema.de.pedidos.service;

import com.example.sistema.de.pedidos.dto.ProdutoDTO;
import com.example.sistema.de.pedidos.entity.ProdutoEntity;
import com.example.sistema.de.pedidos.exception.BusinessException;
import com.example.sistema.de.pedidos.repository.PedidoItemRepository;
import com.example.sistema.de.pedidos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoItemRepository pedidoItemRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveCriarProdutoComSucesso() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Notebook");
        dto.setPreco(new BigDecimal("2500.00"));

        ProdutoEntity produtoSalvo = new ProdutoEntity();
        produtoSalvo.setId(1L);
        produtoSalvo.setNome("Notebook");
        produtoSalvo.setPreco(new BigDecimal("2500.00"));

        when(produtoRepository.save(org.mockito.ArgumentMatchers.any(ProdutoEntity.class)))
                .thenReturn(produtoSalvo);

        ProdutoDTO resultado = produtoService.criarProduto(dto);

        assertEquals("Notebook", resultado.getNome());
        assertEquals(new BigDecimal("2500.00"), resultado.getPreco());
    }

    @Test
    void deveLancarExcecaoAoDeletarProdutoVinculadoAPedido() {
        Long id = 1L;

        ProdutoEntity produto = new ProdutoEntity();
        produto.setId(id);
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("2500.00"));

        when(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produto));
        when(pedidoItemRepository.existsByProdutoId(id)).thenReturn(true);

        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(
                BusinessException.class,
                () -> produtoService.deletarProduto(id)
        );

        assertEquals("Produto não pode ser deletado pois está associado a um pedido", exception.getMessage());

        org.mockito.Mockito.verify(produtoRepository, org.mockito.Mockito.never()).delete(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        Long id = 1L;

        ProdutoEntity produto = new ProdutoEntity();
        produto.setId(id);
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("2500.00"));

        when(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produto));

        ProdutoDTO resultado = produtoService.buscarPorId(id);

        assertEquals(id, resultado.getId());
        assertEquals("Notebook", resultado.getNome());
        assertEquals(new BigDecimal("2500.00"), resultado.getPreco());
    }

    @Test
    void deveLancarResourceNotFoundExceptionAoBuscarProdutoInexistente() {

        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                com.example.sistema.de.pedidos.exception.ResourceNotFoundException.class,
                () -> produtoService.buscarPorId(id)
        );
    }

    @Test
    void deveDeletarProdutoComSucesso() {

        Long id = 1L;

        ProdutoEntity produto = new ProdutoEntity();
        produto.setId(id);
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("2500.00"));

        when(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produto));
        when(pedidoItemRepository.existsByProdutoId(id)).thenReturn(false);

        produtoService.deletarProduto(id);

        org.mockito.Mockito.verify(produtoRepository).delete(produto);
    }
}