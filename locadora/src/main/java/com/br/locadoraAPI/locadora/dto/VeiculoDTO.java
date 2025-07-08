package com.br.locadoraAPI.locadora.dto;

import com.br.locadoraAPI.locadora.model.Veiculo;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class VeiculoDTO {
    private final String placa;
    private final String marca;
    private final String modelo;
    private final Integer anoFabricacao;
    private final BigDecimal valorDiaria;
    private final BigDecimal valorBem;
    private final Boolean alugado;
    private final String tipo;

    public VeiculoDTO(Veiculo veiculo) {
        this.placa = veiculo.getPlaca();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.anoFabricacao = veiculo.getAnoFabricacao();
        this.valorDiaria = veiculo.getValorDiaria();
        this.valorBem = veiculo.getValorBem();
        this.alugado = veiculo.getAlugado();
        this.tipo = veiculo.getTipo();
    }
}