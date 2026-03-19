package com.example.sistema.de.pedidos.controller;

import com.example.sistema.de.pedidos.dto.ClienteDTO;
import com.example.sistema.de.pedidos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteDTO dto){
        ClienteDTO salvo = clienteService.criarCliente(dto);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDTO> clientes = clienteService.listarClientes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id,
                                                       @Valid @RequestBody ClienteDTO dto){
        return ResponseEntity.ok(clienteService.atualizarCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}