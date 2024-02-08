package br.csi.petshop.controller;

import br.csi.petshop.infra.security.TokenServiceJWT;
import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.ClienteRepository;
import br.csi.petshop.model.pet.DadosClientePet;
import br.csi.petshop.model.pet.DadosPet;
import br.csi.petshop.model.pet.Pet;
import br.csi.petshop.service.PetService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private final PetService petService;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final TokenServiceJWT tokenServiceJWT;

    public PetController(PetService petService, ClienteRepository clienteRepository, TokenServiceJWT tokenServiceJWT) {
        this.petService = petService;
        this.clienteRepository = clienteRepository;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity criar(@RequestBody @Valid Pet pet,
                                @RequestHeader("Authorization") String token) {
        Cliente clientePet = pet.getCliente();
        String emailUsuario = extrairIdDoToken(token);
        Cliente clienteToken = null;

        if(clientePet == null){
            clienteToken = clienteRepository.findByEmail(emailUsuario);
            if(clienteToken == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cliente " + clienteToken + " não encontrado na base de dados.");
            }
            clientePet = clienteToken;
        }

        clientePet = clienteRepository.findByEmail(clientePet.getEmail());
        pet.setCliente(clientePet);
        petService.cadastrar(pet);
        return ResponseEntity.created(URI.create("/pet/" + pet.getId())).body(new DadosClientePet(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(),
                pet.getCliente().getId(), pet.getCliente().getNome(), pet.getCliente().getTelefone(),
                pet.getCliente().getEmail(), pet.getCliente().getRua(), pet.getCliente().getBairro(),
                pet.getCliente().getCep(), pet.getCliente().getComplemento(), pet.getCliente().getNumero(),
                pet.getCliente().getUf(), pet.getCliente().getCidade()));
    }

    @GetMapping("/{id}")
    public DadosClientePet findById(@PathVariable Long id) {
        return petService.findPet(id);
    }

    @GetMapping
    public List<DadosClientePet> findAll() {
        return petService.findAllPets();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return petService.deletar(id);
    }

    private String extrairIdDoToken(String token) {
        try {
            String tokenFormatado = token.replace("Bearer","").trim();
            return tokenServiceJWT.getSubject(tokenFormatado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair informações do token JWT", e);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Pet petAtualizado) {
            return petService.atualizar(id, petAtualizado);
    }

}
