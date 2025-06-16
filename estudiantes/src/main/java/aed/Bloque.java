package aed;

import java.util.ArrayList;
import java.util.Arrays;

public class Bloque {
    private int id;
    private MaxHeap<Transaccion> movimientos;
    private int montoMedio;
    private ListaEnlazada<Transaccion> transacciones;
    private ArrayList<Handle> handleTx;

    public class Handle {
        private ListaEnlazada<Transaccion>.Nodo nodo;

        public Handle(ListaEnlazada<Transaccion>.Nodo nodo) {
            this.nodo = nodo;
        }

        public ListaEnlazada<Transaccion>.Nodo nodo() {
            return nodo;
        }

        public void invalidar() {
            nodo = null;
        }

        public boolean activa() {
            return nodo != null;
        }
    }

    public Bloque(int id, Transaccion[] txsOrdenadasPorId) {
        this.id = id;
        movimientos = new MaxHeap<>();
        montoMedio = 0;
        transacciones = new ListaEnlazada<>();
        handleTx = new ArrayList<>();

        ArrayList<Transaccion> txs = new ArrayList<>();

        for (Transaccion tx : txsOrdenadasPorId) {
            ListaEnlazada<Transaccion>.Nodo nodo = transacciones.agregarAtrasConNodo(tx);
            Handle h = new Handle(nodo);
            tx.setHandleLista(h);
            handleTx.add(h);
            txs.add(tx);
        }

        movimientos.maxHeapDesdeSecuencia(txs);
        actualizarParametros(txsOrdenadasPorId);
    }

    public void agregar(Transaccion[] transacciones) {
        ArrayList<Transaccion> _transacciones = new ArrayList<>(Arrays.asList(transacciones));
        movimientos.maxHeapDesdeSecuencia(_transacciones);
        actualizarParametros(transacciones);
    }

    private void actualizarParametros(Transaccion[] transacciones) {
        int suma = 0;
        int conteo = 0;

        for (Transaccion tx : transacciones) {
            if (tx.comprador().id() != 0) {
                suma += tx.monto();
                conteo++;
            }
        }

        montoMedio = (conteo == 0) ? 0 : suma / conteo;
    }

    public Transaccion transaccionMaxima() {
        return movimientos.consultarMaximo();
    }

    public Transaccion eliminarTransaccionMaxima() {
        if (movimientos.tamaño() == 0)
            return null;

        Transaccion tx = movimientos.devolverMaximo();

        Handle h = tx.getHandleLista();
        if (h != null && h.activa()) {
            transacciones.eliminarNodo(h.nodo());
            h.invalidar();
        }

        return tx;
    }

    public void setMontoMedio(int nuevoMontoMedio) {
        this.montoMedio = nuevoMontoMedio;
    }

    public int montoMedio() {
        return montoMedio;
    }

    public int tamañoBloque() {
        return movimientos.tamaño();
    }

    public ArrayList<Transaccion> movimientosArray() {
        return movimientos.obtenerElementos();
    }

    public Transaccion[] obtenerTransacciones() {
        ArrayList<Transaccion> activas = new ArrayList<>();

        for (Handle h : handleTx) {
            if (h.activa()) {
                activas.add(h.nodo().valor);
            }
        }

        return activas.toArray(new Transaccion[0]);
    }
}
