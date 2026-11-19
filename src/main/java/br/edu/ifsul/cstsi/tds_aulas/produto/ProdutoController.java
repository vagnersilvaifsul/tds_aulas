package br.edu.ifsul.cstsi.tds_aulas.produto;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<ProdutoDtoResponse>> findAll(){

        return ResponseEntity.ok(produtoRepository.findAll().stream().map(ProdutoDtoResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDtoResponse> findById(@PathVariable Long id){
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(new ProdutoDtoResponse(produto))) //200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); //404 Not Found
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoDtoResponse>> findByNome(@PathVariable String nome){
        var produtos = produtoRepository.findByNomeStartingWith(nome).stream().map(ProdutoDtoResponse::new).toList();
        return produtos.isEmpty()
                ? ResponseEntity.noContent().build() //204 No Content
                : ResponseEntity.ok(produtos); //200 OK
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody ProdutoDtoPost produtoDTOPost, UriComponentsBuilder uriBuilder){
        var p = produtoRepository.save(new Produto(
                null,
                produtoDTOPost.nome(),
                produtoDTOPost.valorDeCompra(),
                produtoDTOPost.valorDeVenda(),
                produtoDTOPost.descricao(),
                true,
                produtoDTOPost.estoque()
        ));
        var location = uriBuilder.path("api/v1/produtos/{id}").buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoDtoResponse> update(@PathVariable Long id, @RequestBody ProdutoDtoPut produtoDTOPut){
        var p = produtoRepository.save(new Produto(
                id,
                produtoDTOPut.nome(),
                produtoDTOPut.valorDeCompra(),
                produtoDTOPut.valorDeVenda(),
                produtoDTOPut.descricao(),
                produtoDTOPut.situacao(),
                produtoDTOPut.estoque()
        ));
        return p != null ?
                ResponseEntity.ok(new ProdutoDtoResponse(p)) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
