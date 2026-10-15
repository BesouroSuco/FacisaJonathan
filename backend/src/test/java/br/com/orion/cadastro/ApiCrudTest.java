package br.com.orion.cadastro;
import br.com.orion.cadastro.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.net.InetSocketAddress;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class ApiCrudTest {
    // Servidor de protocolo Mongo em memoria, exclusivo dos testes.
    // A aplicacao normal usa o MongoDB configurado em application.properties.
    static final MongoServer mongo = new MongoServer(new MemoryBackend());
    static final InetSocketAddress endereco = mongo.bind();
    @DynamicPropertySource static void banco(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> "mongodb://localhost:" + endereco.getPort() + "/orion_teste");
    }
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired ProdutoRepository produtos;
    @Autowired ClienteRepository clientes;
    @BeforeEach void limpar() { produtos.deleteAll(); clientes.deleteAll(); }
    @AfterAll static void parar() { mongo.shutdownNow(); }
    @Test void crudProduto() throws Exception {
        mvc.perform(get("/api/produtos")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
        String resposta = mvc.perform(post("/api/produtos").contentType("application/json")
          .content("{\"id\":\"forcado\",\"nome\":\"Luminaria\",\"descricao\":\"PLA branco\",\"preco\":150.00}"))
          .andExpect(status().isCreated()).andExpect(header().exists("Location")).andReturn().getResponse().getContentAsString();
        String id = mapper.readTree(resposta).get("id").asText();
        assertNotEquals("forcado", id);
        assertTrue(produtos.existsById(id));
        mvc.perform(get("/api/produtos/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Luminaria"));
        mvc.perform(put("/api/produtos/" + id).contentType("application/json")
          .content("{\"nome\":\"Luminaria Cubic\",\"descricao\":\"PLA bege\",\"preco\":200.00}"))
          .andExpect(status().isOk()).andExpect(jsonPath("$.preco").value(200.0));
        assertEquals("Luminaria Cubic", produtos.findById(id).orElseThrow().getNome());
        mvc.perform(get("/api/produtos")).andExpect(jsonPath("$.length()").value(1));
        mvc.perform(delete("/api/produtos/" + id)).andExpect(status().isNoContent());
        assertFalse(produtos.existsById(id));
        mvc.perform(get("/api/produtos/" + id)).andExpect(status().isNotFound());
    }
    @Test void crudCliente() throws Exception {
        String resposta = mvc.perform(post("/api/clientes").contentType("application/json")
          .content("{\"nome\":\"Ana\",\"telefone\":\"83999999999\",\"email\":\"ana@example.com\"}"))
          .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        String id = mapper.readTree(resposta).get("id").asText();
        mvc.perform(get("/api/clientes/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Ana"));
        mvc.perform(put("/api/clientes/" + id).contentType("application/json")
          .content("{\"nome\":\"Ana Silva\",\"telefone\":\"83988888888\",\"email\":\"ana.silva@example.com\"}"))
          .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Ana Silva"));
        assertEquals("ana.silva@example.com", clientes.findById(id).orElseThrow().getEmail());
        mvc.perform(get("/api/clientes")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));
        mvc.perform(delete("/api/clientes/" + id)).andExpect(status().isNoContent());
        assertFalse(clientes.existsById(id));
    }
    @Test void validaDadosEIdsInexistentes() throws Exception {
        mvc.perform(post("/api/produtos").contentType("application/json")
          .content("{\"nome\":\"\",\"descricao\":\"Teste\",\"preco\":-1}"))
          .andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.nome").exists());
        mvc.perform(post("/api/clientes").contentType("application/json")
          .content("{\"nome\":\"Ana\",\"telefone\":\"123\",\"email\":\"errado\"}"))
          .andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.email").exists());
        mvc.perform(put("/api/clientes/inexistente").contentType("application/json")
          .content("{\"nome\":\"Ana\",\"telefone\":\"83999999999\",\"email\":\"ana@example.com\"}"))
          .andExpect(status().isNotFound());
        mvc.perform(delete("/api/produtos/inexistente")).andExpect(status().isNotFound());
        mvc.perform(post("/api/produtos").contentType("application/json").content("{ quebrado"))
          .andExpect(status().isBadRequest());
        assertEquals(0, produtos.count()); assertEquals(0, clientes.count());
    }
}
