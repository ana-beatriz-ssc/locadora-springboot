package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, String> {
    List<Caminhao> findByCapacidadeCargaGreaterThanEqual(int carga);
}
