package com.example.sistema.de.pedidos.service;

import com.example.sistema.de.pedidos.dto.ClienteDTO;
import com.example.sistema.de.pedidos.entity.ClienteEntity;
import com.example.sistema.de.pedidos.exception.ResourceNotFoundException;
import com.example.sistema.de.pedidos.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Criar cliente
    public ClienteDTO criarCliente(ClienteDTO dto) {
        ClienteEntity entity = new ClienteEntity();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        ClienteEntity salvo = clienteRepository.save(entity);
        return toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public ClienteEntity buscarPorIdEntity(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    // Listar clientes com paginação
    @Transactional(readOnly = true)
    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(this::toDTO);
    }

    // Buscar por ID
    public ClienteDTO buscarPorId(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return toDTO(entity);
    }

    // Atualizar cliente
    public ClienteDTO atualizarCliente(Long id, ClienteDTO dto) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        ClienteEntity atualizado = clienteRepository.save(entity);
        return toDTO(atualizado);
    }

    // Deletar cliente
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    // Converter Entity → DTO
    private ClienteDTO toDTO(ClienteEntity entity) {
        return new ClienteDTO(entity.getId(), entity.getNome(), entity.getEmail());
    }
}