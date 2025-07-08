package com.br.locadoraAPI.locadora.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CAMINHAO")
public class Caminhao extends Veiculo {

    @Column(name = "atributo_extra", nullable = false)
    private Integer capacidadeCarga;

    @Override
    public double calcularSeguro() {
        return getValorBem().doubleValue() * 0.08 / 365;
    }

    @Override
    public String getTipo() {
        return "CAMINHAO";
    }
}


