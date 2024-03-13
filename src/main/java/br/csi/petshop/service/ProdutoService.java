package br.csi.petshop.service;

import br.csi.petshop.model.produto.DadosProduto;
import br.csi.petshop.model.produto.Produto;
import br.csi.petshop.model.produto.ProdutoRepository;
import br.csi.petshop.model.produto.ProdutoTipo;
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

    public ResponseEntity<?> cadastrar(DadosProduto produto) {
        Produto p = new Produto();
        p.setId(produto.id());
        p.setNome(produto.nome());
        p.setDescricao(produto.descricao());
        p.setTipo(produto.tipo());
        p.setPreco(produto.preco());
        produtoRepository.save(p);
        return ResponseEntity.ok(p);
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

    public ResponseEntity<?> atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setTipo(produtoAtualizado.getTipo());

        produtoRepository.save(produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    private DadosProduto convertToDadosProduto(Produto produto) {
        return new DadosProduto(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getTipo());
    }
}
