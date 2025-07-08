package com.br.locadoraAPI.locadora.controller;

import com.br.locadoraAPI.locadora.dto.AluguelRequest;
import com.br.locadoraAPI.locadora.dto.AluguelResponseDTO;
import com.br.locadoraAPI.locadora.dto.DevolucaoRequest;
import com.br.locadoraAPI.locadora.model.Aluguel;
import com.br.locadoraAPI.locadora.repository.AluguelRepository;
import com.br.locadoraAPI.locadora.repository.ClienteRepository;
import com.br.locadoraAPI.locadora.repository.VeiculoRepository;
import com.br.locadoraAPI.locadora.model.Cliente;
import com.br.locadoraAPI.locadora.model.Veiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alugueis")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelRepository aluguelRepo;
    private final VeiculoRepository veiculoRepo;
    private final ClienteRepository clienteRepo;

    @GetMapping
    public List<AluguelResponseDTO> listarTodos() {
        return aluguelRepo.findAll().stream()
                .map(AluguelResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelResponseDTO> buscarPorId(@PathVariable int id) {
        return aluguelRepo.findById(id)
                .map(AluguelResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarAluguel(@RequestBody AluguelRequest request) {
        Veiculo veiculo = veiculoRepo.findById(request.getPlaca()).orElse(null);
        Cliente cliente = clienteRepo.findById(request.getCpf()).orElse(null);

        if (veiculo == null) return ResponseEntity.badRequest().body("Veículo não encontrado");
        if (cliente == null) return ResponseEntity.badRequest().body("Cliente não encontrado");
        if (Boolean.TRUE.equals(veiculo.getAlugado())) return ResponseEntity.badRequest().body("Veículo já alugado");

        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = dataInicio.plusDays(request.getDias());

        Aluguel aluguel = new Aluguel();
        System.out.println("ID antes do save: " + aluguel.getId());  // Deve imprimir null

        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(dataInicio);
        aluguel.setDataFim(dataFim);
        aluguel.setValorTotal(BigDecimal.valueOf(veiculo.calcularAluguel(request.getDias())));
        aluguel.setDias(request.getDias());

        veiculo.setAlugado(true);

        Aluguel salvo = aluguelRepo.save(aluguel);
        System.out.println("Aluguel salvo com ID: " + salvo.getId()); // Deve mostrar ID incremental

        veiculoRepo.save(veiculo);

        return ResponseEntity.ok("Aluguel registrado com sucesso.");
    }


    @PostMapping("/devolver")
    public ResponseEntity<String> registrarDevolucao(@RequestBody DevolucaoRequest request) {
        String placa = request.getPlaca();
        Veiculo veiculo = veiculoRepo.findById(placa).orElse(null);
        if (veiculo == null) return ResponseEntity.badRequest().body("Veículo não encontrado");
        if (!Boolean.TRUE.equals(veiculo.getAlugado())) return ResponseEntity.badRequest().body("Veículo já está disponível");

        veiculo.setAlugado(false);
        veiculoRepo.save(veiculo);
        return ResponseEntity.ok("Veículo devolvido com sucesso.");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAluguel(@PathVariable int id, @RequestBody AluguelRequest request) {
        Aluguel aluguel = aluguelRepo.findById(id).orElse(null);
        if (aluguel == null) {
            return ResponseEntity.notFound().build();
        }

        Veiculo veiculo = veiculoRepo.findById(request.getPlaca()).orElse(null);
        Cliente cliente = clienteRepo.findById(request.getCpf()).orElse(null);

        if (veiculo == null) return ResponseEntity.badRequest().body("Veículo não encontrado");
        if (cliente == null) return ResponseEntity.badRequest().body("Cliente não encontrado");

        // Se o veículo foi alterado, precisamos atualizar o status do veículo antigo e do novo
        if (!aluguel.getVeiculo().getPlaca().equals(request.getPlaca())) {
            // Libera o veículo antigo
            Veiculo veiculoAntigo = aluguel.getVeiculo();
            veiculoAntigo.setAlugado(false);
            veiculoRepo.save(veiculoAntigo);

            // Verifica se o novo veículo está disponível
            if (Boolean.TRUE.equals(veiculo.getAlugado())) {
                return ResponseEntity.badRequest().body("Novo veículo já está alugado");
            }
            
            // Marca o novo veículo como alugado
            veiculo.setAlugado(true);
            veiculoRepo.save(veiculo);
        }

        // Atualiza os dados do aluguel
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDias(request.getDias());
        aluguel.setDataFim(aluguel.getDataInicio().plusDays(request.getDias()));
        aluguel.setValorTotal(BigDecimal.valueOf(veiculo.calcularAluguel(request.getDias())));

        aluguelRepo.save(aluguel);

        return ResponseEntity.ok("Aluguel atualizado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAluguel(@PathVariable int id) {
        Aluguel aluguel = aluguelRepo.findById(id).orElse(null);
        if (aluguel == null) {
            return ResponseEntity.notFound().build();
        }

        // Libera o veículo associado ao aluguel
        Veiculo veiculo = aluguel.getVeiculo();
        veiculo.setAlugado(false);
        veiculoRepo.save(veiculo);

        // Remove o aluguel
        aluguelRepo.delete(aluguel);

        return ResponseEntity.ok("Aluguel deletado com sucesso.");
    }

}
