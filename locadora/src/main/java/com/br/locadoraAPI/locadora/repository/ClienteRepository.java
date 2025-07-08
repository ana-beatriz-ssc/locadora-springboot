package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    // String porque cpf é uma string
}
