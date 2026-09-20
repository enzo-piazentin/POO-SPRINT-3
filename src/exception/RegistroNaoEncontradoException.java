package exception;

/** Indica que uma entidade solicitada não foi encontrada. */
public class RegistroNaoEncontradoException extends RuntimeException {
    public RegistroNaoEncontradoException(String message) { super(message); }
}
