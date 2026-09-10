package Model;

public class Pulverizacao extends IntervencaoOperacional {

    public Pulverizacao(String responsavel) {
        super(responsavel);
    }

    @Override
    public void executarServico(TrechoRodovia trecho) {
        System.out.println("Executando pulverização no trecho KM " + trecho.getKm() + ".");
    }

    @Override
    public String getDescricao(TrechoRodovia trecho) {
        return "KM " + trecho.getKm() + " -> Pulverização (responsável: " + responsavel + ")";
    }
}
