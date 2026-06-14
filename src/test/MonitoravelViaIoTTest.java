package test;

import Model.MonitoravelViaIoT;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public interface MonitoravelViaIoTTest {

    @Test
    default void deveTransmitirDadosSensor() {

        MonitoravelViaIoT monitoravel = new MonitoravelViaIoT() {

            @Override
            public void transmitirDadosSensor() {
                System.out.println("Dados enviados");
            }
        };

        assertNotNull(monitoravel);
    }
}