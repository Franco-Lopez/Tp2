package aed;

import java.util.ArrayList;

public class Bloque {
    private int id;
    private MaxHeap<Transaccion> movimientos;
    private int montoMedio;
    private ListaEnlazada<Transaccion> transacciones;
    private ArrayList<Handle> handleTx;
    private int cantidadActivas;
    private int sumaMontos;
    private int cantidadMontos;

    public class Handle {
        private ListaEnlazada<Transaccion>.Nodo nodo;

        // O(1) - crea instancia
        public Handle(ListaEnlazada<Transaccion>.Nodo nodo) {
            this.nodo = nodo;
        }

        // O(1) - devuelve nodo
        public ListaEnlazada<Transaccion>.Nodo nodo() {
            return nodo;
        }

        // O(1) - invalida el nodo
        public void invalidar() {
            nodo = null;
        }

        // O(1) - comprueba estado del nodo
        public boolean activa() {
            return nodo != null;
        }
    }

    // O(n) - crea instancia de bloque
    public Bloque(int id, Transaccion[] txsOrdenadasPorId) {

        // O(1) - incializa atributos
        this.id = id;
        movimientos = new MaxHeap<>(); // O(1)
        montoMedio = 0;
        transacciones = new ListaEnlazada<>(); // O(1)
        handleTx = new ArrayList<>(txsOrdenadasPorId.length);
        cantidadActivas = 0;
        sumaMontos = 0;
        cantidadMontos = 0;

        ArrayList<Transaccion> txs = new ArrayList<>(txsOrdenadasPorId.length);

        // O(n) - crea secuencia para inicializar maxheap
        for (Transaccion tx : txsOrdenadasPorId) {
            ListaEnlazada<Transaccion>.Nodo nodo = transacciones.agregarAtrasConNodo(tx);
            Handle h = new Handle(nodo);
            handleTx.add(h);
            txs.add(tx);

            cantidadActivas++;
            if (tx.id_comprador() != 0) {
                sumaMontos += tx.monto(); // actualiza montos de usuarios
                cantidadMontos++;
            }
        }

        movimientos.maxHeapDesdeSecuencia(txs); // O(n)
        actualizarMontoMedio(); // O(1)
    }

    // O(1) - devuelve tx maxima (en el maxheap)
    public Transaccion transaccionMaxima() {
        return movimientos.consultarMaximo();
    }

    // O(log n) - elimina tx maxima
    public Transaccion eliminarTransaccionMaxima() {
        if (movimientos.tamaño() == 0)
            return null;

        Transaccion tx = movimientos.devolverMaximo(); // O(log n)

        if (tx.id_comprador() != 0) {
            sumaMontos -= tx.monto();
            cantidadMontos--;
        }

        Handle h = handleTx.get(tx.id());
        if (h != null && h.activa()) {
            transacciones.eliminarNodo(h.nodo()); // O(1)
            h.invalidar();
            cantidadActivas--;
        }

        actualizarMontoMedio(); // O(1)

        return tx;
    }

    // O(1) - actualiza montomedio
    private void actualizarMontoMedio() {
        montoMedio = (cantidadMontos == 0) ? 0 : sumaMontos / cantidadMontos;
    }

    // O(1) - devuelve montomedio
    public int montoMedio() {
        return montoMedio;
    }

    // O(1) - devuelve id
    public int id() {
        return id;
    }

    // O(1) - devuelve cantidad activas
    public int cantidadActivas() {
        return cantidadActivas;
    }

    // O(1) - devuelve tamaño maxheap
    public int tamañoBloque() {
        return movimientos.tamaño();
    }

    // O(n) - devuelve copia de lista heap de movimientos 
    public ArrayList<Transaccion> movimientosArray() {
        return movimientos.obtenerElementos();
    }

    // O(n) - crea lista de transacciones ordenadas por id
    public Transaccion[] obtenerTransacciones() {
        ArrayList<Transaccion> t = transacciones.nodosALista();
        return t.toArray(new Transaccion[t.size()]);
    }

}
