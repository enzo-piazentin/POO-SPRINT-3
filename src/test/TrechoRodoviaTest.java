package test;

import Model.TrechoRodovia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrechoRodoviaTest {
    @Test
    void deveCriarTrechoComDadosValidos() {
        TrechoRodovia trecho = new TrechoRodovia(250, 75.0, "umido", 7);

        assertEquals(250, trecho.getKm());
        assertEquals(75.0, trecho.getAlturaVegetacao());
        assertEquals("umido", trecho.getTipoTerreno());
        assertEquals(7, trecho.getEquipeId());
    }

    @Test
    void deveCalcularCrescimentoDoTerrenoUmido() {
        TrechoRodovia trecho = new TrechoRodovia(1, 20.0, "umido");

        assertEquals(5.0, trecho.calcularCrescimentoDiario());
        trecho.atualizarVegetacao();
        assertEquals(25.0, trecho.getAlturaVegetacao());
    }

    @Test
    void deveCalcularCrescimentoDoTerrenoSeco() {
        TrechoRodovia trecho = new TrechoRodovia(1, 20.0, "seco");

        assertEquals(2.0, trecho.calcularCrescimentoDiario());
    }

    @Test
    void naoDeveAceitarKmNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new TrechoRodovia(-1, 20.0, "seco"));
    }

    @Test
    void naoDeveAceitarAlturaNegativa() {
        assertThrows(IllegalArgumentException.class,
                () -> new TrechoRodovia(1, -1.0, "seco"));
    }

    @Test
    void devePermitirAlterarEquipe() {
        TrechoRodovia trecho = new TrechoRodovia(1, 20.0, "seco");

        trecho.setEquipeId(10);

        assertEquals(10, trecho.getEquipeId());
    }
}
