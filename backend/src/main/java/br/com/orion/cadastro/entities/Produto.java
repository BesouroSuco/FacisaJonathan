package br.com.orion.cadastro.entities;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.math.BigDecimal;

@Document(collection = "produtos")
public class Produto {
    @Id private String id;
    @NotBlank(message = "Informe o nome.") @Size(max = 100)
    private String nome;
    @NotBlank(message = "Informe a descrição.") @Size(max = 500)
    private String descricao;
    @NotNull(message = "Informe o preço.") @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.") @Digits(integer = 8, fraction = 2) @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal preco;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
}
