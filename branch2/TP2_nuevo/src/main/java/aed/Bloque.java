package aed;

import java.util.ArrayList;
import java.util.Arrays;

//import aed.Transaccion;
// import aed.Usuario;

public class Bloque {
    private int id;
    private MaxHeap<Transaccion> movimientos;
    private int montoMedio;
    private ListaEnlazada<Transaccion> transacciones;
    private ArrayList<Handle> handleTx;
    // private Transaccion[] transaccionesOrig; //Lista enlazada que se borre en
    // o(1) con una referencia directa al nodo al momento de querer borrar. Asi
    // mantengo el orden relativo para devolver en O(n) la lista de tx x id

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
            handleTx.add(new Handle(nodo));
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
        int resSuma = 0;
        int resConteo = 0;
        ArrayList<Transaccion> _transacciones = new ArrayList<>(Arrays.asList(transacciones));
        for (int i = 0; i < _transacciones.size(); i++) {
            int valor = _transacciones.get(i).monto();
            Usuario comprador = _transacciones.get(i).comprador();
            Usuario vendedor = _transacciones.get(i).vendedor();

            if (comprador.id() == 0) {
                // vendedor.sumarAMonto(valor);
            } else {
                // comprador.sumarAMonto(-valor);
                // vendedor.sumarAMonto(valor);

                resSuma += valor;
                resConteo += 1;
            }
        }
        if (resConteo == 0) {
            montoMedio = 0;
        } else {
            montoMedio = resSuma / resConteo;
        }
    }

    public Transaccion transaccionMaxima() {
        return movimientos.consultarMaximo();
    }

    public Transaccion eliminarTransaccionMaxima() {
        if (movimientos.tamaño() == 0) {
            return null;
        }
        Transaccion tx = movimientos.devolverMaximo();
        int indice = movimientos.handleDelMaximo().puntero();
        Handle h = handleTx.get(indice);

        if (h.activa()) {
            transacciones.eliminarNodo(h.nodo());
            h.invalidar();
        }
        return tx;// retorno la tx max
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
        ListaEnlazada<Transaccion>.Nodo actual = transacciones.primero();

        while (actual != null) {
            activas.add(actual.valor);
            actual = actual.sig;
        }

        return activas.toArray(new Transaccion[0]);
    }

}
