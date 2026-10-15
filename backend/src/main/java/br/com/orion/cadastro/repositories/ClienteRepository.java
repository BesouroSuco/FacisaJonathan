package br.com.orion.cadastro.repositories;
import br.com.orion.cadastro.entities.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ClienteRepository extends MongoRepository<Cliente, String> { }
