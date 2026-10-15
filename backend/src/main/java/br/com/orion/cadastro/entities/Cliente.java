package br.com.orion.cadastro.entities;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "clientes")
public class Cliente {
    @Id private String id;
    @NotBlank(message = "Informe o nome.") @Size(max = 100)
    private String nome;
    @NotBlank(message = "Informe o telefone.") @Pattern(regexp = "[0-9()+ .-]{8,25}", message = "Informe um telefone válido.")
    private String telefone;
    @NotBlank(message = "Informe o e-mail.") @Email(message = "Informe um e-mail válido.") @Size(max = 150)
    private String email;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
