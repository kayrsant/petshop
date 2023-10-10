package br.csi.petshop.controller;

import br.csi.petshop.infra.security.TokenServiceJWT;
import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.ClienteRepository;
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
        String emailUsuario = extrairIdDoToken(token);
        System.out.println(emailUsuario);

        if(emailUsuario == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cliente não encontrado na base de dados.");
        }

        Cliente cliente = clienteRepository.findByEmail(emailUsuario);
            pet.setCliente(cliente);
            petService.cadastrar(pet);
        return ResponseEntity.created(URI.create("/pet/" + pet.getId())).body(pet);
    }

    @GetMapping("/{id}")
    public DadosPet findById(@PathVariable Long id) {
        return petService.findPet(id);
    }

    @GetMapping
    public List<DadosPet> findAll() {
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
}
