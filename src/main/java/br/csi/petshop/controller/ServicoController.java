package br.csi.petshop.controller;

import br.csi.petshop.model.servico.DadosServico;
import br.csi.petshop.model.servico.Servico;
import br.csi.petshop.model.servico.ServicoRepository;
import br.csi.petshop.service.ServicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private final ServicoService servicoService;

    @Autowired
    private final ServicoRepository servicoRepository;

    public ServicoController(ServicoService servicoService, ServicoRepository servicoRepository) {
        this.servicoService = servicoService;
        this.servicoRepository = servicoRepository;
    }


    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity criar(@RequestBody @Valid Servico servico) {

        DadosServico dadosServico = new DadosServico(servico);

        URI uri = URI.create("/servico/" + servico.getId());

        return servicoService.cadastrar(dadosServico);
    }

    @GetMapping("/{id}")
    public DadosServico findById(@PathVariable Long id) {
        return servicoService.findServico(id);
    }

    @GetMapping
    public List<DadosServico> findAll() {
        return servicoService.findAllServicos();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return servicoService.deletar(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid DadosServico servico){
        return servicoService.atualizar(id, servico);
    }

}
