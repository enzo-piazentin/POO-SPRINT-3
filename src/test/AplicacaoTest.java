package test;

import Model.TrechoRodovia;
import Model.MotorPriorizacao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Teste de fluxo da aplicação sem conexão com o Oracle. */
class AplicacaoTest {
    @Test
    void deveExecutarFluxoDePriorizacaoComTrechosCadastrados() {
        TrechoRodovia[] trechos = {
                new TrechoRodovia(250, 120, "umido"),
                new TrechoRodovia(300, 20, "seco")
        };

        List<String> resultado = new MotorPriorizacao().gerarRelatorio(trechos);

        assertEquals(2, resultado.size());
        assertTrue(resultado.get(0).contains("Roçada Mecanizada"));
        assertTrue(resultado.get(1).contains("Sem intervenção necessária"));
    }
}
