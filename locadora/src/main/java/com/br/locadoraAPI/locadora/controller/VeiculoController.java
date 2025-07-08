package com.br.locadoraAPI.locadora.controller;

import com.br.locadoraAPI.locadora.model.Caminhao;
import com.br.locadoraAPI.locadora.model.Carro;
import com.br.locadoraAPI.locadora.model.Moto;
import com.br.locadoraAPI.locadora.model.Onibus;
import com.br.locadoraAPI.locadora.model.Veiculo;
import com.br.locadoraAPI.locadora.repository.CaminhaoRepository;
import com.br.locadoraAPI.locadora.repository.MotoRepository;
import com.br.locadoraAPI.locadora.repository.VeiculoRepository;
//import com.br.locadoraAPI.locadora.service.VeiculoService;
import com.br.locadoraAPI.locadora.service.VeiculoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoRepository veiculoRepo;
    private final MotoRepository motoRepo;
    private final CaminhaoRepository caminhaoRepo;
    private final VeiculoService veiculoService;

    @GetMapping
    public List<Veiculo> listarTodos() {
        return veiculoRepo.findAll();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Veiculo> buscarPorPlaca(@PathVariable String placa) {
        return veiculoRepo.findById(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/motos/cilindrada")
    public List<Moto> motosPorCilindradaMin(@RequestParam int minCilindrada) {
        return motoRepo.findByCilindradaGreaterThanEqual(minCilindrada);
    }

    @GetMapping("/caminhoes/carga")
    public List<Caminhao> caminhoesPorCarga(@RequestParam int minCarga) {
        return caminhaoRepo.findByCapacidadeCargaGreaterThanEqual(minCarga);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Veiculo>> listarPorTipo(@PathVariable int tipo) {
        List<Veiculo> veiculos;
        
        switch (tipo) {
            case 1:
                veiculos = veiculoRepo.findByTipo(Moto.class);
                break;
            case 2:
                veiculos = veiculoRepo.findByTipo(Carro.class);
                break;
            case 3:
                veiculos = veiculoRepo.findByTipo(Caminhao.class);
                break;
            case 4:
                veiculos = veiculoRepo.findByTipo(Onibus.class);
                break;
            case 0:
                veiculos = veiculoRepo.findAll();
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(veiculos);
    }

    @PostMapping
    public ResponseEntity<Veiculo> inserir(@RequestBody Veiculo veiculo) {
        if (veiculoRepo.existsById(veiculo.getPlaca())) {
            return ResponseEntity.status(409).build(); // CONFLICT
        }
        return ResponseEntity.ok(veiculoRepo.save(veiculo));
    }

    @PutMapping("/{placa}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable String placa, @RequestBody Veiculo novo) {
        return veiculoRepo.findById(placa).map(v -> {
            novo.setPlaca(placa); // manter a placa original
            return ResponseEntity.ok(veiculoRepo.save(novo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> remover(@PathVariable String placa) {
        if (!veiculoRepo.existsById(placa)) {
            return ResponseEntity.notFound().build();
        }
        veiculoRepo.deleteById(placa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/depreciar")
    public ResponseEntity<Void> depreciarVeiculos(
            @RequestParam int tipo,
            @RequestParam double taxa) {
        veiculoService.depreciarVeiculos(tipo, taxa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/aumentar-diaria")
    public ResponseEntity<Void> aumentarDiaria(
            @RequestParam int tipo,
            @RequestParam double taxa) {
        
        // Validação básica dos parâmetros
        if (tipo < 0 || tipo > 4) {
            return ResponseEntity.badRequest().build();
        }
        
        /*if (taxa <= 0) {
            return ResponseEntity.badRequest().build();
        }*/

        veiculoService.aumentarDiaria(tipo, taxa);
        return ResponseEntity.ok().build();
    }
}
