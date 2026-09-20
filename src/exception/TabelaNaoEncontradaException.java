package exception;

/**
 * Indica que uma tabela necessária não existe no schema Oracle conectado.
 */
public class TabelaNaoEncontradaException extends RuntimeException {
    public TabelaNaoEncontradaException(String tabela, Throwable cause) {
        super("A tabela " + tabela + " não existe no schema Oracle conectado. "
                + "Execute seu-script-criacao.sql usando o mesmo usuário da conexão.", cause);
    }
}
