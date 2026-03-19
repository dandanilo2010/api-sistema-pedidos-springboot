package com.example.sistema.de.pedidos.controller;

import com.example.sistema.de.pedidos.dto.ProdutoDTO;
import com.example.sistema.de.pedidos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@Valid @RequestBody ProdutoDTO dto){
        ProdutoDTO salvo = produtoService.criarProduto(dto);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listarProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProdutoDTO> produtos = produtoService.listarProdutos(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id,
                                                       @Valid @RequestBody ProdutoDTO dto){
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}