package exception;

/** Erro de infraestrutura ao executar uma operação JDBC. */
public class BancoDadosException extends RuntimeException {
    public BancoDadosException(String message, Throwable cause) { super(message, cause); }
    public BancoDadosException(String message) { super(message); }
}
