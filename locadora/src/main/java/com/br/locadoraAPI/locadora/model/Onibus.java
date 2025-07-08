package com.br.locadoraAPI.locadora.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ONIBUS")
public class Onibus extends Veiculo {

    @Column(name = "atributo_extra", nullable = false)
    private Integer passageiros; // Este campo representa a capacidade de passageiros

    // Adicione getter e setter se estiver usando Lombok apenas para campos específicos
    public Integer getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(Integer passageiros) {
        this.passageiros = passageiros;
    }

    @Override
    public double calcularSeguro() {
        return getValorBem().doubleValue() * 0.20 / 365;
    }

    @Override
    public String getTipo() {
        return "ONIBUS";
    }
}