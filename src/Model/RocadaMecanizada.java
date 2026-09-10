package Model;

/** Simple: roçada mecanizada. */
public class RocadaMecanizada extends IntervencaoOperacional {

    public RocadaMecanizada(String responsavel) {
        super(responsavel);
    }

    @Override
    public void executarServico(TrechoRodovia trecho) {
        System.out.println("Executando roçada mecanizada no trecho KM " + trecho.getKm() + ".");
    }

    @Override
    public String getDescricao(TrechoRodovia trecho) {
        return "KM " + trecho.getKm() + " -> Roçada Mecanizada (responsável: " + responsavel + ")";
    }
}
