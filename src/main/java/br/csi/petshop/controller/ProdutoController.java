package br.csi.petshop.controller;

import br.csi.petshop.model.produto.DadosProduto;
import br.csi.petshop.model.produto.Produto;
import br.csi.petshop.model.produto.ProdutoRepository;
import br.csi.petshop.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private final ProdutoService produtoService;

    @Autowired
    private final ProdutoRepository produtoRepository;


    public ProdutoController(ProdutoService produtoService, ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity criar(@RequestBody @Valid Produto produto) {
        produtoService.cadastrar(produto);
        return ResponseEntity.created(URI.create("/produto/" + produto.getId())).body(produto);
    }

    @GetMapping("/{id}")
    public DadosProduto findById(@PathVariable Long id) {
        return produtoService.findProduto(id);
    }

    @GetMapping
    public List<DadosProduto> findAll() {
        return produtoService.findAllProdutos();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return produtoService.deletar(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Produto produtoAtualizado) {
        return produtoService.atualizar(id, produtoAtualizado);
    }

}
