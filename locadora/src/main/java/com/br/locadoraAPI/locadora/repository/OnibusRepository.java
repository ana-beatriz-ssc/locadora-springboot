package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Onibus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnibusRepository extends JpaRepository<Onibus, String> {
    List<Onibus> findByPassageirosGreaterThanEqual(int capacidade);
}
