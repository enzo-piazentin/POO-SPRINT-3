package Main;


import Model.MotorPriorizacao;
import Model.TrechoRodovia;

public class Main {

    public static void main(String[] args) {

        TrechoRodovia[] trechos = {
                new TrechoRodovia(10, 120, "umido"),
                new TrechoRodovia(25, 80, "seco"),
                new TrechoRodovia(40, 30, "seco")
        };

        MotorPriorizacao motor = new MotorPriorizacao();

        var relatorio = motor.gerarRelatorio(trechos);

        relatorio.forEach(System.out::println);
    }
}