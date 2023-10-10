package br.csi.petshop.service;

import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.DadosCliente;
import br.csi.petshop.model.cliente.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void cadastrar(Cliente cliente, String token) {
            clienteRepository.save(cliente);
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

    private DadosCliente convertToDadosCliente(Cliente cliente) {
        return new DadosCliente(cliente.getId(), cliente.getNome(), cliente.getTelefone(),
                cliente.getEmail(), cliente.getRua(), cliente.getBairro(),
                cliente.getCep(), cliente.getComplemento(), cliente.getNumero(),
                cliente.getUf(), cliente.getCidade());
    }
}