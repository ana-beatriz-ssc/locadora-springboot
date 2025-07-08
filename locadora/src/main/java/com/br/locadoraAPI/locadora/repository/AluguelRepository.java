package com.br.locadoraAPI.locadora.repository;

import com.br.locadoraAPI.locadora.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {

    List<Aluguel> findByVeiculoPlaca(String placa);

    List<Aluguel> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    //List<Aluguel> findByVeiculoTipo(String tipo);

    @Query("SELECT a FROM Aluguel a WHERE a.veiculo.class = :tipo")
    List<Aluguel> findByVeiculoTipo(@Param("tipo") String tipo);
}
