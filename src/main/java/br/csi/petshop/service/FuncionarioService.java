package br.csi.petshop.service;

import br.csi.petshop.model.funcionario.DadosFuncionario;
import br.csi.petshop.model.funcionario.Funcionario;
import br.csi.petshop.model.funcionario.FuncionarioRepository;
import br.csi.petshop.model.usuario.Usuario;
import br.csi.petshop.model.usuario.UsuarioPermissao;
import br.csi.petshop.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, UsuarioRepository usuarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public void cadastrar(Funcionario funcionario, String token) {
        Usuario u = new Usuario();
        Usuario t = this.usuarioRepository.findByLogin(funcionario.getEmail());
        u.setLogin(funcionario.getEmail());
        u.setSenha(new BCryptPasswordEncoder().encode("123456789"));
        u.setPermissao(UsuarioPermissao.FUNCIONARIO);

        funcionarioRepository.save(funcionario);
        usuarioRepository.save(u);
    }

    public ResponseEntity<?> deletar(Long id){
        if(funcionarioRepository.existsById(id)){
            funcionarioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Funcionário com id " + id + " deletado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
    }

    public DadosFuncionario findFuncionario(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        return convertToDadosFuncionario(funcionario);
    }

    public List<DadosFuncionario> findAllFuncionarios() {
        List<Funcionario> funcionarios= funcionarioRepository.findAll();

        return funcionarios.stream()
                .map(this::convertToDadosFuncionario)
                .collect(Collectors.toList());
    }

    private DadosFuncionario convertToDadosFuncionario(Funcionario funcionario) {
        return new DadosFuncionario(funcionario.getId(), funcionario.getNome(), funcionario.getCargo(), funcionario.getTelefone(), funcionario.getEmail(), funcionario.getRua(), funcionario.getBairro(), funcionario.getCep(), funcionario.getComplemento(), funcionario.getNumero(), funcionario.getUf(), funcionario.getCidade());
    }
}
