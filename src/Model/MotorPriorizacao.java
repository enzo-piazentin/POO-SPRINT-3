package Model;

import java.util.ArrayList;
import java.util.List;

public class MotorPriorizacao {

    public List<String> gerarRelatorio(TrechoRodovia[] trechos) {

        List<String> relatorio = new ArrayList<>();

        for (TrechoRodovia trecho : trechos) {

            IntervencaoOperacional intervencao = null;

            if (trecho.getAlturaVegetacao() >= 100) {
                intervencao = new RocadaMecanizada("Equipe Mecanizada");
            } else if (trecho.getAlturaVegetacao() >= 60) {
                intervencao = new RocadaManual("Equipe Manual");
            } else if (trecho.getAlturaVegetacao() >= 30) {
                // exemplo: para alturas intermediárias podemos pulverizar
                intervencao = new Pulverizacao("Equipe Pulverização");
            }

            if (intervencao != null) {
                // executar a intervenção de forma polimórfica
                intervencao.executarServico(trecho);
                // adicionar descrição ao relatório
                relatorio.add(intervencao.getDescricao(trecho));
            } else {
                // tratar caso sem intervenção necessária
                relatorio.add("KM " + trecho.getKm() + " -> Sem intervenção necessária");
            }
        }

        return relatorio;
    }
}
