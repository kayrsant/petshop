package br.csi.petshop.controller;

import br.csi.petshop.model.funcionario.DadosFuncionario;
import br.csi.petshop.model.funcionario.Funcionario;
import br.csi.petshop.model.usuario.UsuarioRepository;
import br.csi.petshop.service.FuncionarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private final FuncionarioService funcionarioService;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public FuncionarioController(FuncionarioService funcionarioService, UsuarioRepository usuarioRepository) {
        this.funcionarioService = funcionarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity criar(@RequestBody @Valid Funcionario funcionario,
                                @RequestHeader("Authorization") String token) {
            funcionarioService.cadastrar(funcionario, token);
            URI uri = URI.create("/funcionario/" + funcionario.getId());
            return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }


    @GetMapping("/{id}")
    public DadosFuncionario findById(@PathVariable Long id) {
        return funcionarioService.findFuncionario(id);
    }

    @GetMapping
    public List<DadosFuncionario> findAll() {
        return funcionarioService.findAllFuncionarios();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return funcionarioService.deletar(id);
    }


}
