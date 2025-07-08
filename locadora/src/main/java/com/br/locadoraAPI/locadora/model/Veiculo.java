package com.br.locadoraAPI.locadora.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Getter
@Setter
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Carro.class, name = "CARRO"),
    @JsonSubTypes.Type(value = Moto.class, name = "MOTO"),
    @JsonSubTypes.Type(value = Caminhao.class, name = "CAMINHAO"),
    @JsonSubTypes.Type(value = Onibus.class, name = "ONIBUS")
})
public abstract class Veiculo {
    @Id
    @Column(length = 10, nullable = false)
    private String placa;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Aluguel> alugueis = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private String marca;

    @Column(length = 50, nullable = false)
    private String modelo;

    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @Column(name = "valor_diaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    @Column(name = "valor_bem", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorBem;

    @Column(nullable = false)
    private Boolean alugado = false;

    //@Column(name = "tipo")
    //private String tipo;

    public abstract double calcularSeguro();

    public double calcularAluguel(int dias) {
        return (valorDiaria.doubleValue() + calcularSeguro()) * dias;
    }

    public boolean estaDisponivel() {
        return !Boolean.TRUE.equals(alugado);
    }

    public abstract String getTipo();

    public void depreciar(double taxaDepreciacao) {
    BigDecimal fator = BigDecimal.valueOf(1 - taxaDepreciacao / 100);
    this.valorBem = this.valorBem.multiply(fator).setScale(2, RoundingMode.HALF_UP);
    }

    public void aumentarDiaria(double taxaAumento) {
    BigDecimal fator = BigDecimal.valueOf(1 + taxaAumento / 100);
    this.valorDiaria = this.valorDiaria.multiply(fator)
    .setScale(2, RoundingMode.HALF_UP);
    }
}
