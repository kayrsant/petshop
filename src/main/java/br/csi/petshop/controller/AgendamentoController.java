package br.csi.petshop.controller;

import br.csi.petshop.model.agendamento.Agendamento;
import br.csi.petshop.model.agendamento.DadosAgendamento;
import br.csi.petshop.model.agendamento.ProdutoAgendamento;
import br.csi.petshop.model.agendamento.ServicoAgendamento;
import br.csi.petshop.model.produto.Produto;
import br.csi.petshop.model.produto.ProdutoRepository;
import br.csi.petshop.model.servico.Servico;
import br.csi.petshop.model.servico.ServicoRepository;
import br.csi.petshop.service.AgendamentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
    @Autowired
    private final AgendamentoService agendamentoService;

    @Autowired
    private final ServicoRepository servicoRepository;

    @Autowired
    private final ProdutoRepository produtoRepository;

    public AgendamentoController(AgendamentoService agendamentoService, ServicoRepository servicoRepository, ProdutoRepository produtoRepository) {
        this.agendamentoService = agendamentoService;
        this.servicoRepository = servicoRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid Agendamento agendamento,
                                @RequestHeader("Authorization") String token) {
        URI uri = URI.create("/agendamento/" + agendamento.getId());

        return agendamentoService.cadastrar(agendamento);
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

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Agendamento agendamentoAtualizado) {
        return agendamentoService.atualizar(id, agendamentoAtualizado);
    }
}
