package br.com.orion.cadastro.services;
import br.com.orion.cadastro.entities.Cliente;
import br.com.orion.cadastro.repositories.ClienteRepository;
import br.com.orion.cadastro.exceptions.NaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
@Service
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    public List<Cliente> listar() { return repository.findAll(Sort.by("nome")); }
    public Cliente buscar(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado."));
    }
    public Cliente criar(Cliente dados) {
        dados.setId(null); // O banco gera o identificador; o cliente não escolhe o ID.
        return repository.save(dados);
    }
    public Cliente atualizar(String id, Cliente dados) {
        Cliente atual = buscar(id);
        atual.setNome(dados.getNome());
        atual.setTelefone(dados.getTelefone());
        atual.setEmail(dados.getEmail());
        return repository.save(atual);
    }
    public void excluir(String id) { repository.delete(buscar(id)); }
}
