package br.csi.petshop.service;

import br.csi.petshop.model.produto.DadosProduto;
import br.csi.petshop.model.produto.Produto;
import br.csi.petshop.model.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrar(Produto produto) {
        produtoRepository.save(produto);
    }

    public ResponseEntity<?> deletar(Long id){
        Produto produto = this.produtoRepository.findById(id).orElse(null);

        if(produto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        this.produtoRepository.delete(produto);
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + produto.getId() + " deletado com sucesso.");
    }

    public DadosProduto findProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return convertToDadosProduto(produto);
    }

    public List<DadosProduto> findAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(this::convertToDadosProduto)
                .collect(Collectors.toList());
    }

    private DadosProduto convertToDadosProduto(Produto produto) {
        return new DadosProduto(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getTipo());
    }
}
