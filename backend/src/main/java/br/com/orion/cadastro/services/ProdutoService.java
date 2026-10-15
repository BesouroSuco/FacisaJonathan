package br.com.orion.cadastro.services;
import br.com.orion.cadastro.entities.Produto;
import br.com.orion.cadastro.repositories.ProdutoRepository;
import br.com.orion.cadastro.exceptions.NaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    public ProdutoService(ProdutoRepository repository) { this.repository = repository; }
    public List<Produto> listar() { return repository.findAll(Sort.by("nome")); }
    public Produto buscar(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto não encontrado."));
    }
    public Produto criar(Produto dados) {
        dados.setId(null); // O banco gera o identificador; o cliente não escolhe o ID.
        return repository.save(dados);
    }
    public Produto atualizar(String id, Produto dados) {
        Produto atual = buscar(id);
        atual.setNome(dados.getNome());
        atual.setDescricao(dados.getDescricao());
        atual.setPreco(dados.getPreco());
        return repository.save(atual);
    }
    public void excluir(String id) { repository.delete(buscar(id)); }
}
