package Model;

public class Pulverizacao extends IntervencaoOperacional {

    public Pulverizacao(String responsavel) {
        super(responsavel);
    }

    @Override
    public void executarServico() {
        System.out.println("Executando pulverização.");
    }
}
