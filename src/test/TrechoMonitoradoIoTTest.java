package test;

import Model.TrechoMonitoradoIoT;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TrechoMonitoradoIoTTest {
    @Test
    void deveTransmitirDadosDoSensor() {
        TrechoMonitoradoIoT trecho = new TrechoMonitoradoIoT(250, 75, "seco");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(saida, true, StandardCharsets.UTF_8));
            trecho.transmitirDadosSensor();
        } finally {
            System.setOut(original);
        }

        assertTrue(saida.toString(StandardCharsets.UTF_8).contains("KM 250"));
        assertTrue(saida.toString(StandardCharsets.UTF_8).contains("75.0 cm"));
    }
}
