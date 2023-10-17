package br.csi.petshop.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import br.csi.petshop.model.usuario.DadosUsuario;
import br.csi.petshop.model.usuario.Usuario;
import br.csi.petshop.model.usuario.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setToken("");
        this.repository.save(usuario);
    }

    public DadosUsuario findUsuario(Long id) {
        Usuario usuario = this.repository.getReferenceById(id);
        return new DadosUsuario(usuario);
    }

    public boolean existsUsuarioEmail(String email) {
        Usuario usuario = this.repository.findByLogin(email);
        return usuario == null;
    }

    public List<DadosUsuario> findAllUsuarios() {
        return this.repository.findAll().stream().map(DadosUsuario::new).toList();
    }

    public ResponseEntity<?> deletar(Long id) {
        Usuario u = this.repository.getReferenceById(id);
        Optional<Usuario> usuario = this.repository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        this.repository.delete(u);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário com id " + id + " deletado com sucesso.");
    }

    public ResponseEntity<?> atualizar(Long id, Usuario usuarioAtualizado) {
        try {
            Optional<Usuario> usuarioExistenteOpt = this.repository.findById(id);

            if (usuarioExistenteOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            Usuario usuarioExistente = usuarioExistenteOpt.get();

            usuarioExistente.setLogin(usuarioAtualizado.getLogin());
            usuarioExistente.setPermissao(usuarioAtualizado.getPermissao());

            String senhaAtualizada = usuarioAtualizado.getSenha();
            if (senhaAtualizada != null && !senhaAtualizada.isEmpty()) {
                usuarioExistente.setSenha(new BCryptPasswordEncoder().encode(senhaAtualizada));
            }
            this.repository.save(usuarioExistente);

            return ResponseEntity.ok(new DadosUsuario(usuarioExistente.getId(), usuarioExistente.getLogin(), usuarioExistente.getPermissao()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

}
