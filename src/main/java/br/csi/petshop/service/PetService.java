package br.csi.petshop.service;

import br.csi.petshop.model.pet.DadosPet;
import br.csi.petshop.model.pet.Pet;
import br.csi.petshop.model.pet.PetRepository;
import br.csi.petshop.model.servico.DadosServico;
import br.csi.petshop.model.servico.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void cadastrar(Pet pet) {
        petRepository.save(pet);
    }

    public ResponseEntity<?> deletar(Long id){
        Pet pet = this.petRepository.findById(id).orElse(null);

        if(pet == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado.");
        }
        this.petRepository.delete(pet);
        return ResponseEntity.status(HttpStatus.OK).body("Pet " + pet.getId() + " deletado");
    }

    public DadosPet findPet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        return convertToDadosPet(pet);
    }

    public List<DadosPet> findAllPets() {
        List<Pet> pets = petRepository.findAll();

        return pets.stream()
                .map(this::convertToDadosPet)
                .collect(Collectors.toList());
    }

    private DadosPet convertToDadosPet(Pet pet) {
        return new DadosPet(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(), pet.getCliente().getId(), pet.getCliente().getNome());
    }

    public ResponseEntity<?> atualizar(Long id, Pet petAtualizado) {
        Optional<Pet> optionalPet = petRepository.findById(id);

        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();

            pet.setNome(petAtualizado.getNome());
            pet.setIdade(petAtualizado.getIdade());
            pet.setRaca(petAtualizado.getRaca());
            pet.setTipo(petAtualizado.getTipo());

            petRepository.save(pet);

            return ResponseEntity.ok().body("Pet " + id + " atualizado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado.");
        }
    }

}
