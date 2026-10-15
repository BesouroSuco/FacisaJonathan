package br.com.orion.cadastro.controllers;
import br.com.orion.cadastro.entities.Produto;
import br.com.orion.cadastro.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service) { this.service = service; }
    @GetMapping public List<Produto> listar() { return service.listar(); }
    @GetMapping("/{id}") public Produto buscar(@PathVariable String id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<Produto> criar(@Valid @RequestBody Produto dados) {
        Produto criado = service.criar(dados);
        return ResponseEntity.created(URI.create("/api/produtos/" + criado.getId())).body(criado);
    }
    @PutMapping("/{id}") public Produto atualizar(@PathVariable String id, @Valid @RequestBody Produto dados) {
        return service.atualizar(id, dados);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
