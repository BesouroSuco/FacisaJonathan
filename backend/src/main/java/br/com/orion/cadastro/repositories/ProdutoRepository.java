package br.com.orion.cadastro.repositories;
import br.com.orion.cadastro.entities.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ProdutoRepository extends MongoRepository<Produto, String> { }
