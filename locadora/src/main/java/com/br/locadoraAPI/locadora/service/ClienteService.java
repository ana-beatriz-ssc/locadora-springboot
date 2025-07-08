package com.br.locadoraAPI.locadora.service;

import com.br.locadoraAPI.locadora.model.Cliente;
import com.br.locadoraAPI.locadora.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepo;

    public List<Cliente> listarTodos() {
        return clienteRepo.findAll();
    }

    public Cliente salvar(Cliente c) {
        return clienteRepo.save(c);
    }

    public boolean existe(String cpf) {
        return clienteRepo.existsById(cpf);
    }
}
