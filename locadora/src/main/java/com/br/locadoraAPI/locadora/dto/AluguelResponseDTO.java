package com.br.locadoraAPI.locadora.dto;

import com.br.locadoraAPI.locadora.model.Aluguel;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AluguelResponseDTO {
    private final Integer id;
    private final VeiculoDTO veiculo;
    private final ClienteResumoDTO cliente;
    private final LocalDateTime dataInicio;
    private final LocalDateTime dataFim;
    private final Integer dias;
    private final BigDecimal valorTotal;

    public AluguelResponseDTO(Aluguel aluguel) {
        this.id = aluguel.getId();
        this.veiculo = new VeiculoDTO(aluguel.getVeiculo());
        this.cliente = new ClienteResumoDTO(aluguel.getCliente());
        this.dataInicio = aluguel.getDataInicio();
        this.dataFim = aluguel.getDataFim();
        this.dias = aluguel.getDias();
        this.valorTotal = aluguel.getValorTotal();
    }
}