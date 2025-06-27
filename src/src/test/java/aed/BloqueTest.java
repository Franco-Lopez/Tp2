package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BloqueTest {

    @Test
    public void testAgregarYTamaño() {
        Transaccion[] iniciales = new Transaccion[] {};
        Bloque bloque = new Bloque(1, iniciales);

        Transaccion t1 = new Transaccion(0, 0, 10, 100);
        Transaccion t2 = new Transaccion(1, 5, 20, 200);
        Transaccion t3 = new Transaccion(2, 7, 15, 50);

        bloque.agregar(new Transaccion[]{t1, t2, t3});

        assertEquals(3, bloque.tamañoBloque());
    }

    @Test
    public void testTransaccionMaxima() {
        Transaccion[] iniciales = new Transaccion[] {};
        Bloque bloque = new Bloque(2, iniciales);

        Transaccion t1 = new Transaccion(0, 0, 10, 100);
        Transaccion t2 = new Transaccion(1, 5, 20, 200);
        Transaccion t3 = new Transaccion(2, 7, 15, 50);

        bloque.agregar(new Transaccion[]{t1, t2, t3});

        assertEquals(t2, bloque.transaccionMaxima());
    }

    @Test
    public void testMontoMedio() {
        Transaccion[] iniciales = new Transaccion[] {};
        Bloque bloque = new Bloque(3, iniciales);

        Transaccion t1 = new Transaccion(0, 0, 10, 100);
        Transaccion t2 = new Transaccion(1, 5, 20, 200);
        Transaccion t3 = new Transaccion(2, 7, 15, 50);

        bloque.agregar(new Transaccion[]{t1, t2, t3});

        assertEquals(125, bloque.montoMedio());
    }

}
