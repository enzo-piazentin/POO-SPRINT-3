package test;

import Model.MotorPriorizacao;
import Model.TrechoRodovia;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MotorPriorizacaoTest {
    @Test
    void deveGerarUmaLinhaParaCadaTrecho() {
        TrechoRodovia[] trechos = {
                new TrechoRodovia(10, 120, "umido"),
                new TrechoRodovia(20, 80, "seco"),
                new TrechoRodovia(30, 40, "seco"),
                new TrechoRodovia(40, 20, "seco")
        };

        List<String> relatorio = new MotorPriorizacao().gerarRelatorio(trechos);

        assertEquals(4, relatorio.size());
        assertTrue(relatorio.get(0).contains("Roçada Mecanizada"));
        assertTrue(relatorio.get(1).contains("Roçada Manual"));
        assertTrue(relatorio.get(2).contains("Pulverização"));
        assertTrue(relatorio.get(3).contains("Sem intervenção necessária"));
    }

    @Test
    void deveTratarListaVazia() {
        List<String> relatorio = new MotorPriorizacao().gerarRelatorio(new TrechoRodovia[0]);

        assertTrue(relatorio.isEmpty());
    }
}
