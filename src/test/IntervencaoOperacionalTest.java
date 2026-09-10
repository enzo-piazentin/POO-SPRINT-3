package test;

import Model.IntervencaoOperacional;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/** Teste: verifica que IntervencaoOperacional é abstrata. */
class IntervencaoOperacionalTest {

    @Test
    void naoDevePermitirInstanciarClasseAbstrata() {

        assertTrue(
                Modifier.isAbstract(
                        IntervencaoOperacional.class.getModifiers()
                )
        );
    }
}
