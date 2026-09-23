package test;

import model.IntervencaoRegistro;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IntervencaoRegistroTest {
    @Test
    void deveCriarRegistroComDataAtualPorPadrao() {
        IntervencaoRegistro registro = new IntervencaoRegistro(
                250, "ROCADA_MECANIZADA", "Equipe Leste");

        assertEquals(250, registro.kmTrecho());
        assertEquals("ROCADA_MECANIZADA", registro.tipo());
        assertEquals("Equipe Leste", registro.responsavel());
        assertEquals(LocalDate.now(), registro.dataIntervencao());
    }

    @Test
    void deveRejeitarKmNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new IntervencaoRegistro(-1, "ROCADA", "Equipe"));
    }

    @Test
    void deveRejeitarTipoOuResponsavelVazios() {
        assertThrows(IllegalArgumentException.class,
                () -> new IntervencaoRegistro(1, "", "Equipe"));
        assertThrows(IllegalArgumentException.class,
                () -> new IntervencaoRegistro(1, "ROCADA", ""));
    }
}
