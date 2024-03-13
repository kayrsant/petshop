package br.csi.petshop.model.pet;

import br.csi.petshop.model.cliente.Cliente;

public record DadosPet(Long id, String nome, int idade, String raca, PetTipo tipo, Cliente cliente) {

    public DadosPet(DadosPet pet){
        this(pet.id(), pet.nome(), pet.idade(), pet.raca(), pet.tipo(), pet.cliente());
    }

    public DadosPet(Long id, String nome, int idade, String raca, PetTipo tipo, Cliente cliente) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.raca = raca;
        this.tipo = tipo;
        this.cliente = cliente;
    }
}