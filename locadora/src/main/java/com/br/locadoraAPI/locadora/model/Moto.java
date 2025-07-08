package com.br.locadoraAPI.locadora.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("MOTO")
public class Moto extends Veiculo {
    @Column(name = "atributo_extra", nullable = false)  // reutiliza a mesma coluna
    private int cilindrada;

    @Override
    public double calcularSeguro() {
        return getValorBem().doubleValue() * 0.11 / 365;
    }

    @Override
    public String getTipo() {
        return "MOTO";
    }
}
