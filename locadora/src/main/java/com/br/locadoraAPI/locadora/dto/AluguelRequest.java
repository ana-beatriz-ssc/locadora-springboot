package com.br.locadoraAPI.locadora.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelRequest {
    private String placa;
    private String cpf;
    private int dias;
}
