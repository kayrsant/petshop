package br.csi.petshop.controller;

import br.csi.petshop.model.agendamento.Agendamento;
import br.csi.petshop.model.agendamento.DadosAgendamento;
import br.csi.petshop.model.funcionario.DadosFuncionario;
import br.csi.petshop.model.funcionario.Funcionario;
import br.csi.petshop.service.AgendamentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
    @Autowired
    private final AgendamentoService agendamentoService;
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity criar(@RequestBody @Valid Agendamento agendamento,
                                @RequestHeader("Authorization") String token) {
        agendamentoService.cadastrar(agendamento);
        URI uri = URI.create("/agendamento/" + agendamento.getId());
        return ResponseEntity.status(HttpStatus.OK).body(agendamento);
    }

    @GetMapping("/{id}")
    public DadosAgendamento findById(@PathVariable Long id) {
        return agendamentoService.findAgendamento(id);
    }

    @GetMapping
    public List<DadosAgendamento> findAll() {
        return agendamentoService.findAllAgendamentos();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return agendamentoService.deletar(id);
    }
}
