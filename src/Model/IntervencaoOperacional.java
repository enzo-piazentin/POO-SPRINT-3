package Model;

public abstract class IntervencaoOperacional {

    protected final String responsavel;

    public IntervencaoOperacional(String responsavel) {
        this.responsavel = responsavel;
    }

    // Agora a intervenção conhece o trecho onde atua
    public abstract void executarServico(TrechoRodovia trecho);

    // Descrição textual para relatórios
    public abstract String getDescricao(TrechoRodovia trecho);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (responsável: " + responsavel + ")";
    }
}
