package Model;

import java.util.ArrayList;
import java.util.List;

/** Simple: motor que decide intervenção por altura da vegetação. */
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
                intervencao = new Pulverizacao("Equipe Pulverização");
            }

            if (intervencao != null) {
                intervencao.executarServico(trecho);
                relatorio.add(intervencao.getDescricao(trecho));
            } else {
                relatorio.add("KM " + trecho.getKm() + " -> Sem intervenção necessária");
            }
        }

        return relatorio;
    }
}
