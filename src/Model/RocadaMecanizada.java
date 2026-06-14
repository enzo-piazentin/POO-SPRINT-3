package Model;

public class RocadaMecanizada extends IntervencaoOperacional {

    public RocadaMecanizada(String responsavel) {
        super(responsavel);
    }

    @Override
    public void executarServico() {
        System.out.println("Executando roçada mecanizada.");
    }
}
