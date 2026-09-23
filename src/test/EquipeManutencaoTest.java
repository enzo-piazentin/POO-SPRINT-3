package test;

import model.EquipeManutencao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipeManutencaoTest {
    @Test
    void deveNormalizarDirecaoParaMaiusculas() {
        EquipeManutencao equipe = new EquipeManutencao(
                "Equipe Leste", "ROCADA", "leste");

        assertEquals("LESTE", equipe.direcao());
    }

    @Test
    void deveAceitarSomenteDirecoesPermitidas() {
        assertDoesNotThrow(() -> new EquipeManutencao("Norte", "ROCADA", "NORTE"));
        assertDoesNotThrow(() -> new EquipeManutencao("Sul", "ROCADA", "SUL"));
        assertDoesNotThrow(() -> new EquipeManutencao("Leste", "ROCADA", "LESTE"));
        assertDoesNotThrow(() -> new EquipeManutencao("Oeste", "ROCADA", "OESTE"));
    }

    @Test
    void deveRejeitarDirecaoInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new EquipeManutencao("Equipe", "ROCADA", "CENTRO"));
    }

    @Test
    void deveRejeitarNomeOuEspecialidadeVazios() {
        assertThrows(IllegalArgumentException.class,
                () -> new EquipeManutencao("", "ROCADA", "NORTE"));
        assertThrows(IllegalArgumentException.class,
                () -> new EquipeManutencao("Equipe", "", "NORTE"));
    }
}
