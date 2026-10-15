package br.com.orion.cadastro.controllers;
import br.com.orion.cadastro.entities.Cliente;
import br.com.orion.cadastro.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }
    @GetMapping public List<Cliente> listar() { return service.listar(); }
    @GetMapping("/{id}") public Cliente buscar(@PathVariable String id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente dados) {
        Cliente criado = service.criar(dados);
        return ResponseEntity.created(URI.create("/api/clientes/" + criado.getId())).body(criado);
    }
    @PutMapping("/{id}") public Cliente atualizar(@PathVariable String id, @Valid @RequestBody Cliente dados) {
        return service.atualizar(id, dados);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
