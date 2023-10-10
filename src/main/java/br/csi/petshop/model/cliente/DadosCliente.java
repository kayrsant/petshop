package br.csi.petshop.model.cliente;


public record DadosCliente(Long id, String nome, String telefone, String email,
                           String rua, String bairro, String cep,
                           String complemento, String numero, String uf, String cidade) {
    public DadosCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getTelefone(),
                cliente.getEmail(), cliente.getRua(), cliente.getBairro(),
                cliente.getCep(), cliente.getComplemento(), cliente.getNumero(),
                cliente.getUf(), cliente.getCidade());
    }
}