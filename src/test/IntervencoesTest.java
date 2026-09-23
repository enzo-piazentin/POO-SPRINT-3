package test;

import Model.Pulverizacao;
import Model.RocadaManual;
import Model.RocadaMecanizada;
import Model.TrechoRodovia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervencoesTest {
    private final TrechoRodovia trecho = new TrechoRodovia(250, 75, "seco");

    @Test
    void deveDescreverRocadaMecanizada() {
        String descricao = new RocadaMecanizada("Equipe Norte").getDescricao(trecho);

        assertTrue(descricao.contains("KM 250"));
        assertTrue(descricao.contains("Roçada Mecanizada"));
        assertTrue(descricao.contains("Equipe Norte"));
    }

    @Test
    void deveDescreverRocadaManual() {
        String descricao = new RocadaManual("Equipe Sul").getDescricao(trecho);

        assertTrue(descricao.contains("Roçada Manual"));
        assertTrue(descricao.contains("Equipe Sul"));
    }

    @Test
    void deveDescreverPulverizacao() {
        String descricao = new Pulverizacao("Equipe Leste").getDescricao(trecho);

        assertTrue(descricao.contains("Pulverização"));
        assertTrue(descricao.contains("Equipe Leste"));
    }
}
