package br.csi.petshop.model.servico;

public record DadosServico(Long id, String nome, String descricao, double preco) {
    public DadosServico(Servico servico){
        this(servico.getId(), servico.getNome(), servico.getDescricao(), servico.getPreco());
    }
}
