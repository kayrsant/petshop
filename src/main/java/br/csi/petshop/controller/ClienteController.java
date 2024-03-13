package br.csi.petshop.controller;

import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.DadosCliente;
import br.csi.petshop.model.cliente.DadosClienteCadastro;
import br.csi.petshop.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid Cliente cliente,
                                              @RequestHeader("Authorization") String token) {

        URI uri = URI.create("/clientes/" + cliente.getId());

        DadosClienteCadastro dadosClienteCadastro = DadosClienteCadastro.fromCliente(cliente);

        return clienteService.cadastrar(dadosClienteCadastro, token);
    }

    @GetMapping("/{id}")
    public DadosCliente findById(@PathVariable Long id) {
        return clienteService.findCliente(id);
    }

    @GetMapping
    public List<DadosCliente> findAll() {
        return clienteService.findAllClientes();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return clienteService.deletar(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid Cliente clienteAtualizado) {
        return clienteService.atualizar(id, clienteAtualizado);
    }

}


