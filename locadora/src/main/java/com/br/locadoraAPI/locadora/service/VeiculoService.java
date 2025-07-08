package com.br.locadoraAPI.locadora.service;

import com.br.locadoraAPI.locadora.model.Caminhao;
import com.br.locadoraAPI.locadora.model.Carro;
import com.br.locadoraAPI.locadora.model.Moto;
import com.br.locadoraAPI.locadora.model.Onibus;
import com.br.locadoraAPI.locadora.model.Veiculo;
import com.br.locadoraAPI.locadora.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepo;
        
    public List<Veiculo> listarTodos() {
        return veiculoRepo.findAll();
    }

    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepo.save(veiculo);
    }

    public boolean existe(String placa) {
        return veiculoRepo.existsById(placa);
    }

    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepo.findById(placa).orElse(null);
    }
   
    public void depreciarVeiculos(int tipo, double taxaDepreciacao) {
        List<Veiculo> selecionados;
        
        switch (tipo) {
            case 1: 
                selecionados = veiculoRepo.findByTipo(Moto.class);
                break;
            case 2: 
                selecionados = veiculoRepo.findByTipo(Carro.class);
                break;
            case 3: 
                selecionados = veiculoRepo.findByTipo(Caminhao.class);
                break;
            case 4: 
                selecionados = veiculoRepo.findByTipo(Onibus.class);
                break;
            case 0: 
                selecionados = veiculoRepo.findAll();
                break;
            default: 
                selecionados = List.of();
        }

        selecionados.forEach(v -> v.depreciar(taxaDepreciacao));
        veiculoRepo.saveAll(selecionados);
    }

        public void aumentarDiaria(int tipo, double taxaAumento) {
        List<Veiculo> selecionados;
        
        switch (tipo) {
            case 1: 
                selecionados = veiculoRepo.findByTipo(Moto.class);
                break;
            case 2: 
                selecionados = veiculoRepo.findByTipo(Carro.class);
                break;
            case 3: 
                selecionados = veiculoRepo.findByTipo(Caminhao.class);
                break;
            case 4: 
                selecionados = veiculoRepo.findByTipo(Onibus.class);
                break;
            case 0: 
                selecionados = veiculoRepo.findAll();
                break;
            default: 
                selecionados = List.of();
        }

        selecionados.forEach(v -> v.aumentarDiaria(taxaAumento));
        veiculoRepo.saveAll(selecionados);
    }
}
