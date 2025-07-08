package com.br.locadoraAPI.locadora.dto;

import com.br.locadoraAPI.locadora.model.Cliente;
import lombok.Getter;

@Getter
public class ClienteResumoDTO {
    private final String cpf;
    private final String nome;

    public ClienteResumoDTO(Cliente cliente) {
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
    }
}