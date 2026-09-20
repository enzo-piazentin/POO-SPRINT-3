package exception;

/** Erro de validação dos dados enviados para persistência. */
public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException(String message) { super(message); }
}
