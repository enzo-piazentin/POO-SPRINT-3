package Model;

/** Simple: ação operacional executada em um trecho. */
public abstract class IntervencaoOperacional {

    protected final String responsavel;

    public IntervencaoOperacional(String responsavel) {
        this.responsavel = responsavel;
    }

    /** Executa o serviço no trecho fornecido. */
    public abstract void executarServico(TrechoRodovia trecho);

    /** Retorna descrição curta da intervenção para relatórios. */
    public abstract String getDescricao(TrechoRodovia trecho);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (responsável: " + responsavel + ")";
    }
}
