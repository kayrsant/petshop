package br.csi.petshop.model.produto;

public enum ProdutoTipo {
    REMEDIO,
    BRINQUEDO,
    ALIMENTO,
    ACESSORIO;

    public static boolean contains(String tipo) {
        for (ProdutoTipo produtoTipo : ProdutoTipo.values()) {
            if (produtoTipo.name().equals(tipo)) {
                return true;
            }
        }
        return false;
    }
}
