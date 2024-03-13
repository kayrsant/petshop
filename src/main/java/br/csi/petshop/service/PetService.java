package br.csi.petshop.service;

import br.csi.petshop.model.pet.DadosClientePet;
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

    public ResponseEntity<?> cadastrar(DadosClientePet pet) {

        Pet newPet = new Pet();
        newPet.setIdade(pet.idade());
        newPet.setNome(pet.nome());
        newPet.setRaca(pet.raca());
        newPet.setTipo(pet.tipo());
        newPet.setCliente(pet.cliente());

        petRepository.save(newPet);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPet);
    }

    public ResponseEntity<?> deletar(Long id){
        Pet pet = this.petRepository.findById(id).orElse(null);

        if(pet == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado.");
        }
        this.petRepository.delete(pet);
        return ResponseEntity.status(HttpStatus.OK).body("Pet " + pet.getId() + " deletado");
    }

    public DadosClientePet findPet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        return convertToDadosPet(pet);
    }

    public List<DadosClientePet> findAllPets() {
        List<Pet> pets = petRepository.findAll();

        return pets.stream()
                .map(this::convertToDadosPet)
                .collect(Collectors.toList());
    }

    private DadosClientePet convertToDadosPet(Pet pet) {
        return new DadosClientePet(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(), pet.getCliente());
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
