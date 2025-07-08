package com.br.locadoraAPI.locadora.service;

import com.br.locadoraAPI.locadora.model.Aluguel;
import com.br.locadoraAPI.locadora.repository.AluguelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepo;

    public List<Aluguel> listarTodos() {
        return aluguelRepo.findAll();
    }

    public Aluguel salvar(Aluguel aluguel) {
        return aluguelRepo.save(aluguel);
    }

    public void remover(Integer id) {
        aluguelRepo.deleteById(id);
    }

    public Aluguel buscarPorId(Integer id) {
        return aluguelRepo.findById(id).orElse(null);
    }
}
