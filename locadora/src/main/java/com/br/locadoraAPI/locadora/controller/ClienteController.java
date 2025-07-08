package com.br.locadoraAPI.locadora.controller;

import com.br.locadoraAPI.locadora.dto.ClienteDetalhadoDTO;
import com.br.locadoraAPI.locadora.dto.ClienteResumoDTO;
import com.br.locadoraAPI.locadora.model.Cliente;
import com.br.locadoraAPI.locadora.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepository clienteRepo;

    @GetMapping
    public List<ClienteResumoDTO> listarTodos() {
        return clienteRepo.findAll().stream()
                .map(ClienteResumoDTO::new)
                .toList();
    }


    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDetalhadoDTO> buscarPorCpf(@PathVariable String cpf) {
        return clienteRepo.findById(cpf)
            .map(cliente -> ResponseEntity.ok(new ClienteDetalhadoDTO(cliente)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
        if (clienteRepo.existsById(cliente.getCpf())) {
            return ResponseEntity.status(409).build(); // CONFLICT
        }
        return ResponseEntity.ok(clienteRepo.save(cliente));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String cpf, @RequestBody Cliente novo) {
        return clienteRepo.findById(cpf).map(c -> {
            novo.setCpf(cpf);
            return ResponseEntity.ok(clienteRepo.save(novo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> remover(@PathVariable String cpf) {
        if (!clienteRepo.existsById(cpf)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepo.deleteById(cpf);
        return ResponseEntity.noContent().build();
    }
}
