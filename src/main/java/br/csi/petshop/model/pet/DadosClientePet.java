package br.csi.petshop.model.pet;

import br.csi.petshop.model.cliente.Cliente;

public record DadosClientePet(Long id, String nome, int idade, String raca, PetTipo tipo, Cliente cliente) {

    public DadosClientePet(Pet pet){
        this(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(), pet.getCliente());
    }

}

