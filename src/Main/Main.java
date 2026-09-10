package Main;

import Model.MotorPriorizacao;
import Model.TrechoRodovia;
import Model.TrechoMonitoradoIoT;

/** Simple: classe principal para demonstração. */
public class Main {

    public static void main(String[] args) {

        TrechoRodovia[] trechos = {
                new TrechoMonitoradoIoT(10, 120, "umido"),
                new TrechoRodovia(25, 80, "seco"),
                new TrechoRodovia(40, 30, "seco")
        };

        MotorPriorizacao motor = new MotorPriorizacao();

        var relatorio = motor.gerarRelatorio(trechos);

        relatorio.forEach(System.out::println);
    }
}
