package br.csi.petshop.model.pet;

import br.csi.petshop.model.cliente.Cliente;

public record DadosPet(Long id, String nome, int idade, String raca, PetTipo tipo, Cliente cliente) {

    public DadosPet(Pet pet){
        this(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(), pet.getCliente());
    }
}
