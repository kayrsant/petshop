package br.csi.petshop.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public UsuarioService (UsuarioRepository repository){
        this.repository = repository;
    }

    public void cadastrar(Usuario usuario){
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
            usuario.setToken("");


            this.repository.save(usuario);
    }
    public DadosUsuario findUsuario(Long id){
        Usuario usuario = this.repository.getReferenceById(id);
        return new DadosUsuario(usuario);
    }

    public boolean existsUsuarioEmail(String email){
        Usuario usuario = this.repository.findByLogin(email);
        return usuario == null;
    }
    public List<DadosUsuario> findAllUsuarios(){
        return this.repository.findAll().stream().map(DadosUsuario::new).toList();
    }

    public ResponseEntity<?> deletar(Long id){
        Usuario u = this.repository.getReferenceById(id);
        Optional<Usuario> usuario = this.repository.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
            this.repository.delete(u);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário com id " + id + " deletado com sucesso.");
    }
}
