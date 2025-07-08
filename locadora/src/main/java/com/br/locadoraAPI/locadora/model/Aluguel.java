package com.br.locadoraAPI.locadora.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aluguel")
@Getter
@Setter
public class Aluguel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "placa_veiculo", referencedColumnName = "placa", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cpf_cliente", referencedColumnName = "cpf", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "dias", nullable = false)
    private Integer dias;


    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    /**
     * Calcula os dias entre dataInicio e dataFim.
     * Pode ser utilizado para fins de lógica interna,
     * sem persistência no banco de dados.
     */
    public long calcularDias() {
        if (dataInicio != null && dataFim != null) {
            return ChronoUnit.DAYS.between(
                dataInicio.toLocalDate(),
                dataFim.toLocalDate()
            );
        }
        return 0;
    }
}
