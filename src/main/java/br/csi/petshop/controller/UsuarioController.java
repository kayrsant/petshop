package br.csi.petshop.controller;

import br.csi.petshop.model.usuario.DadosUsuario;
import br.csi.petshop.model.usuario.Usuario;
import br.csi.petshop.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<Object> criar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriComponentsBuilder) {
        try {
            this.service.cadastrar(usuario);
            URI uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosUsuario(usuario.getId(), usuario.getLogin(), usuario.getPermissao()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            DadosUsuario dadosUsuario = this.service.findUsuario(id);
            return ResponseEntity.ok(dadosUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            List<DadosUsuario> usuarios = this.service.findAllUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao recuperar usuários: " + e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return service.deletar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid Usuario usuarioAtualizado) {
        return (ResponseEntity<Object>) service.atualizar(id, usuarioAtualizado);
    }
}
