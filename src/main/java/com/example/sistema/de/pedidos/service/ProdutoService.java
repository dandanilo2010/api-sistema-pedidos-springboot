package com.example.sistema.de.pedidos.service;

import com.example.sistema.de.pedidos.dto.ProdutoDTO;
import com.example.sistema.de.pedidos.entity.ProdutoEntity;
import com.example.sistema.de.pedidos.exception.BusinessException;
import com.example.sistema.de.pedidos.exception.ResourceNotFoundException;
import com.example.sistema.de.pedidos.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Criar produto a partir de DTO
    public ProdutoDTO criarProduto(ProdutoDTO dto){
        ProdutoEntity entity = new ProdutoEntity();
        entity.setNome(dto.getNome());
        entity.setPreco(dto.getPreco());
        ProdutoEntity salvo = produtoRepository.save(entity);
        return toDTO(salvo);
    }

    // Listar produtos com paginação
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> listarProdutos(Pageable pageable){
        return produtoRepository.findAll(pageable)
                .map(this::toDTO);
    }

    // Buscar produto por ID
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id){
        ProdutoEntity produto = buscarPorIdEntity(id);
        return toDTO(produto);
    }

    // Atualizar produto
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto){
        ProdutoEntity produto = buscarPorIdEntity(id);
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        ProdutoEntity atualizado = produtoRepository.save(produto);
        return toDTO(atualizado);
    }

    // Deletar produto
    public void deletarProduto(Long id){
        ProdutoEntity produto = buscarPorIdEntity(id);
        if(produto.getPedidos() != null && !produto.getPedidos().isEmpty()){
            throw new BusinessException("Produto não pode ser deletado pois está associado a um pedido");
        }
        produtoRepository.delete(produto);
    }

    @Transactional(readOnly = true)
    public ProdutoEntity buscarPorIdEntity(Long id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }



    public ProdutoDTO toDTO(ProdutoEntity produto) {
        return new ProdutoDTO(produto);
    }
}