package br.csi.petshop.model.cliente;

import br.csi.petshop.model.pet.Pet;

import java.util.List;
import java.util.stream.Collectors;

public record DadosCliente(Long id, String nome, String telefone, String email,
                           String rua, String bairro, String cep,
                           String complemento, String numero, String uf, String cidade, List<Pet> pets) {
    public DadosCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getTelefone(),
                cliente.getEmail(), cliente.getRua(), cliente.getBairro(),
                cliente.getCep(), cliente.getComplemento(), cliente.getNumero(),
                cliente.getUf(), cliente.getCidade(), cliente.getPets());
    }

    public static DadosCliente fromCliente(Cliente cliente) {
        return new DadosCliente(
                cliente.getId(), cliente.getNome(), cliente.getTelefone(),
                cliente.getEmail(), cliente.getRua(), cliente.getBairro(),
                cliente.getCep(), cliente.getComplemento(), cliente.getNumero(),
                cliente.getUf(), cliente.getCidade(), cliente.getPets()
        );
    }

    public static List<DadosCliente> fromClientes(List<Cliente> clientes) {
        return clientes.stream()
                .map(DadosCliente::fromCliente)
                .collect(Collectors.toList());
    }
}
