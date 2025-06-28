package aed;

import java.util.ArrayList;

public class Bloque {
    private int id;
    private MaxHeap<Transaccion> movimientos;
    private int montoMedio;
    private ListaEnlazada<Transaccion> transacciones;
  //  private ArrayList<Handle> handleTx;

    private int cantidadActivas;
    private int sumaMontos;
    private int cantidadMontos;

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
      //  handleTx = new ArrayList<>(txsOrdenadasPorId.length);

        cantidadActivas = 0;
        sumaMontos = 0;
        cantidadMontos = 0;

        ArrayList<Transaccion> txs = new ArrayList<>(txsOrdenadasPorId.length);

        for (Transaccion tx : txsOrdenadasPorId) {
            ListaEnlazada<Transaccion>.Nodo nodo = transacciones.agregarAtrasConNodo(tx);
            Handle h = new Handle(nodo);
            tx.setHandleLista(h);
           // handleTx.add(h);
            txs.add(tx);

            cantidadActivas++;
            if (tx.id_comprador() != 0) {
                sumaMontos += tx.monto();
                cantidadMontos++;
            }
        }

        movimientos.maxHeapDesdeSecuencia(txs);
        actualizarMontoMedio();
    }

    public void agregar(Transaccion[] nuevasTxs) {
        for (Transaccion tx : nuevasTxs) {
            movimientos.encolar(tx);
        }

        for (Transaccion tx : nuevasTxs) {
            ListaEnlazada<Transaccion>.Nodo nodo = transacciones.agregarAtrasConNodo(tx);
            Handle h = new Handle(nodo);
            tx.setHandleLista(h);
        //    handleTx.add(h);

            if (tx.id_comprador() != 0) {
                sumaMontos += tx.monto();
                cantidadMontos++;
            }

            cantidadActivas++;
        }

        actualizarMontoMedio();
    }

    public Transaccion transaccionMaxima() {
        return movimientos.consultarMaximo();
    }

    public Transaccion eliminarTransaccionMaxima() {
        if (movimientos.tamaño() == 0)
            return null;

        Transaccion tx = movimientos.devolverMaximo();

        if (tx.id_comprador() != 0) {
            sumaMontos -= tx.monto();
            cantidadMontos--;
        }

        Handle h = tx.getHandleLista();
        if (h != null && h.activa()) {
            transacciones.eliminarNodo(h.nodo());
            h.invalidar();
            cantidadActivas--;
        }

        actualizarMontoMedio();

        return tx;
    }

    private void actualizarMontoMedio() {
        montoMedio = (cantidadMontos == 0) ? 0 : sumaMontos / cantidadMontos;
    }

    public int montoMedio() {
        return montoMedio;
    }

    public int id() {
        return id;
    }

    public int cantidadActivas() {
        return cantidadActivas;
    }

    public int tamañoBloque() {
        return movimientos.tamaño();
    }

    public ArrayList<Transaccion> movimientosArray() {
        return movimientos.obtenerElementos();
    }

    public Transaccion[] obtenerTransacciones() {
        ArrayList<Transaccion> activas = new ArrayList<>(cantidadActivas);
        ListaEnlazada<Transaccion>.Nodo actual = transacciones.primero();
        while (actual != null) {
            Transaccion tx = actual.valor;
            if (tx.getHandleLista() != null && tx.getHandleLista().activa()) {
                activas.add(tx);
            }
            actual = actual.sig;
        }
        return activas.toArray(new Transaccion[0]);
    }
}
