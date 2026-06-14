package Model;

import java.util.ArrayList;
import java.util.List;

public class MotorPriorizacao {

    public List<String> gerarRelatorio(TrechoRodovia[] trechos) {

        List<String> relatorio = new ArrayList<>();

        for (TrechoRodovia trecho : trechos) {

            if (trecho.getAlturaVegetacao() >= 100) {
                relatorio.add(
                        "KM " + trecho.getKm()
                                + " -> Roçada Mecanizada"
                );

            } else if (trecho.getAlturaVegetacao() >= 60) {
                relatorio.add(
                        "KM " + trecho.getKm()
                                + " -> Roçada Manual"
                );
            }
        }

        return relatorio;
    }
}
