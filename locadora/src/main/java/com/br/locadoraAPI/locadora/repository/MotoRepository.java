package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, String> {
    List<Moto> findByCilindradaGreaterThanEqual(int cilindrada);
}
