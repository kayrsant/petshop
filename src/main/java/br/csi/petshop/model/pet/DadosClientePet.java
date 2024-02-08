package br.csi.petshop.model.pet;

public record DadosClientePet(Long id, String nome, int idade, String raca, PetTipo tipo,
                              Long idCliente, String nomeCliente, String telefone, String email,
                              String rua, String bairro, String cep, String complemento,
                              String numero, String uf, String cidade) {

    public DadosClientePet(Pet pet){
        this(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(),
                pet.getCliente().getId(), pet.getCliente().getNome(), pet.getCliente().getTelefone(),
                pet.getCliente().getEmail(), pet.getCliente().getRua(), pet.getCliente().getBairro(),
                pet.getCliente().getCep(), pet.getCliente().getComplemento(), pet.getCliente().getNumero(),
                pet.getCliente().getUf(), pet.getCliente().getCidade()
                );
    }

}

