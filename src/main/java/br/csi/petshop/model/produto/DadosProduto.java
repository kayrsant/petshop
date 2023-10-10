package br.csi.petshop.model.produto;

public record DadosProduto(Long id, String nome, String descricao, double preco, ProdutoTipo tipo) {
    public DadosProduto(Produto produto){
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getTipo());
    }
}
