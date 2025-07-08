package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, String> {
    List<Carro> findByAutonomiaGreaterThanEqual(int autonomia);
}
