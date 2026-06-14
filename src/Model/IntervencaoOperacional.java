package Model;

public abstract class IntervencaoOperacional {

    protected String responsavel;

    public IntervencaoOperacional(String responsavel) {
        this.responsavel = responsavel;
    }

    public abstract void executarServico();
}
