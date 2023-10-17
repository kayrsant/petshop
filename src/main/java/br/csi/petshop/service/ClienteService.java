package br.csi.petshop.service;

import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.DadosCliente;
import br.csi.petshop.model.cliente.ClienteRepository;
import br.csi.petshop.model.usuario.Usuario;
import br.csi.petshop.model.usuario.UsuarioPermissao;
import br.csi.petshop.model.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrar(Cliente cliente, String token) {
        Usuario u = new Usuario();
        Usuario t = this.usuarioRepository.findByLogin(cliente.getEmail());
        u.setLogin(cliente.getEmail());
        u.setSenha(new BCryptPasswordEncoder().encode("123456789"));
        u.setPermissao(UsuarioPermissao.CLIENTE);

        clienteRepository.save(cliente);
        usuarioRepository.save(u);
    }

    public ResponseEntity<?> deletar(Long id){
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente com id " + id + " deletado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
    }

    public DadosCliente findCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Converte a entidade para DadosCliente
        return convertToDadosCliente(cliente);
    }

    public List<DadosCliente> findAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        // Converte a lista de entidades para uma lista de DadosCliente
        return clientes.stream()
                .map(this::convertToDadosCliente)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setRua(clienteAtualizado.getRua());
                    cliente.setBairro(clienteAtualizado.getBairro());
                    cliente.setCep(clienteAtualizado.getCep());
                    cliente.setComplemento(clienteAtualizado.getComplemento());
                    cliente.setNumero(clienteAtualizado.getNumero());
                    cliente.setUf(clienteAtualizado.getUf());
                    cliente.setCidade(clienteAtualizado.getCidade());

                    clienteRepository.save(cliente);
                    return ResponseEntity.ok("Cliente com id " + id + " atualizado com sucesso.");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado."));
    }

    private DadosCliente convertToDadosCliente(Cliente cliente) {
        return new DadosCliente(cliente.getId(), cliente.getNome(), cliente.getTelefone(),
                cliente.getEmail(), cliente.getRua(), cliente.getBairro(),
                cliente.getCep(), cliente.getComplemento(), cliente.getNumero(),
                cliente.getUf(), cliente.getCidade());
    }
}