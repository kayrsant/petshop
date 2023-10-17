package br.csi.petshop.model.pet;

public record DadosPet(Long id, String nome, int idade, String raca, PetTipo tipo, Long idCliente, String clienteNome) {

    public DadosPet(Pet pet){
        this(pet.getId(), pet.getNome(), pet.getIdade(), pet.getRaca(), pet.getTipo(), pet.getCliente().getId(), pet.getCliente().getNome());
    }
}
