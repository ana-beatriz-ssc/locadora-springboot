package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Veiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {
    @Query("SELECT v FROM Veiculo v WHERE TYPE(v) = :classe")
    List<Veiculo> findByTipo(@Param("classe") Class<? extends Veiculo> classe);
}
