package br.com.orion.cadastro.exceptions;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataAccessException;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<?> naoEncontrado(NaoEncontradoException e) {
        return ResponseEntity.status(404).body(Map.of("mensagem", e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validar(MethodArgumentNotValidException e) {
        Map<String, String> campos = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(erro -> campos.put(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Confira os campos do formulário.", "campos", campos));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> jsonInvalido() {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "JSON ou valor de campo inválido."));
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> bancoIndisponivel() {
        return ResponseEntity.status(503).body(Map.of("mensagem", "Não foi possível acessar o MongoDB. Confira a conexão."));
    }
}
