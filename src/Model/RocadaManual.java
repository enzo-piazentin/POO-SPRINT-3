package Model;

public class RocadaManual extends IntervencaoOperacional {

    public RocadaManual(String responsavel) {
        super(responsavel);
    }

    @Override
    public void executarServico(TrechoRodovia trecho) {
        System.out.println("Executando roçada manual no trecho KM " + trecho.getKm() + ".");
    }

    @Override
    public String getDescricao(TrechoRodovia trecho) {
        return "KM " + trecho.getKm() + " -> Roçada Manual (responsável: " + responsavel + ")";
    }
}
