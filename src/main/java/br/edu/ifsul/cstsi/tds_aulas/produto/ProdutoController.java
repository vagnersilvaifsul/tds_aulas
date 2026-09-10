package br.edu.ifsul.cstsi.tds_aulas.produto;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProdutoDto>> findAll() {
        return ResponseEntity.ok(
                produtoRepository.findAll().stream()
                        .map(ProdutoDto::new)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable(value = "id") Long id) {
        var produto = produtoRepository.findById(id);
        if(produto.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produto.map(ProdutoDto::new).get());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> findByNome(@PathVariable(value = "nome") String nome) {
        return ResponseEntity.ok(produtoRepository.findByNomeQuerySpeakSQL(nome).get());
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Produto produto, UriComponentsBuilder uriBuilder){
        var p = produtoRepository.save(produto);
        var location = uriBuilder.path("api/v1/produtos/{id}").buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public String update(@PathVariable(value = "id") Long id, @RequestBody Produto produto) {
        return "update " + produto;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        return "delete " + id;
    }
}
