package br.csi.petshop.model.funcionario;

public record DadosFuncionario(Long id, String nome, String cargo, String telefone, String email,
                               String rua, String bairro, String cep,
                               String complemento, String numero, String uf, String cidade) {
    public DadosFuncionario(Funcionario funcionario){
        this(funcionario.getId(), funcionario.getNome(), funcionario.getCargo(), funcionario.getTelefone(), funcionario.getEmail(), funcionario.getRua(), funcionario.getBairro(), funcionario.getCep(), funcionario.getComplemento(), funcionario.getNumero(), funcionario.getUf(), funcionario.getCidade());
    }
}
