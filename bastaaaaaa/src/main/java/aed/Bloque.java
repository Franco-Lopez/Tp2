package aed;

// import java.util.ArrayList;
// import java.util.Iterator;

// import aed.ListaDoblementeEnlazada.ListaIterador;

public class Bloque {
    private int id;
    private MaxHeap<Transaccion> transaccionesHeap;
    private ListaDoblementeEnlazada<Transaccion> transaccionesLista;
    private ListaDoblementeEnlazada<HandleLista<Transaccion>> handles;
    private int montoMedio;

    public Bloque(int id) {
        this.id = id;
        transaccionesHeap = new MaxHeap<>();
        transaccionesLista = new ListaDoblementeEnlazada<>();
        handles = new ListaDoblementeEnlazada<>();
        montoMedio = 0;
    }

    public void agregar(Transaccion[] txs) {
        for (Transaccion t : txs) {
            transaccionesHeap.encolar(t);
            transaccionesLista.agregarAtras(t);
            Nodo<Transaccion> t_nodo = new Nodo<Transaccion>(t);
            handles.agregarAtras(new HandleLista<>(t_nodo));
        }
        actualizarParametros(txs);
    }

    private void actualizarParametros(Transaccion[] txs) {
        int suma = 0;
        int conteo = 0;
        for (Transaccion t : txs) {
            Usuario comprador = t.comprador();
            Usuario vendedor = t.vendedor();
            int monto = t.monto();
            if (t.esDeCreacion()) {
                vendedor.sumarAMonto(monto);
            } else {
                vendedor.sumarAMonto(monto);
                comprador.sumarAMonto(-monto);
                suma += monto;
                conteo += 1;
            }
        }
        montoMedio = (conteo == 0) ? 0 : suma / conteo;
    }

    public Transaccion transaccionMaxima() {
        return transaccionesHeap.consultarMaximo();
    }

    public int montoMedio() {
        return montoMedio;
    }

    public int tamañoBloque() {
        return transaccionesHeap.tamaño();
    }

    // public ArrayList<Transaccion> movimientosArray() {
    //     return transaccionesHeap.obtenerElementos();
    // }

    public void actualizarTransaccion(Transaccion t) {
        int tamaño = transaccionesHeap.tamaño();
        for (int i = 0; i < tamaño; i++) {
            if (handles.obtener(i).valor().id() == t.id()) {
                transaccionesHeap.actualizarClave(transaccionesHeap.handleDe(i), t);
            }
        }
        // for (HandleHeap<Transaccion> h : transaccionesHeap) {
        //     if (h.getValor() == t) {
        //         transaccionesHeap.actualizarClave(h, t);
        //         break;
        //     }
        // }   
    }

    // public void eliminarTransaccion(Transaccion t) {
    //     HandleHeap<Transaccion> h = transaccionesHeap.handleDe(t.id());
    //     transaccionesHeap.eliminar(h);
    //     transaccionesLista.eliminar(t.id());
    //     handles.eliminar(t.id());
    //     // HandleHeap<Transaccion> handleEliminar = null;
    //     // int pos = -1;
    //     // for (int i = 0; i < transaccionesLista.longitud(); i++) {
    //     //     if (transaccionesLista.obtener(i).getValor() == t) {
    //     //         handleEliminar = transaccionesLista.get(i);
    //     //         pos = i;
    //     //         break;
    //     //     }
    //     // }
    //     // if (handleEliminar != null) {
    //     //     movimientos.eliminar(handleEliminar);
    //     //     transaccionesLista.remove(pos);
    //     //     // Buscar índice de t en la lista enlazada para eliminarlo
    //     //     int indiceLista = buscarIndiceTransaccion(t);
    //     //     if (indiceLista != -1) {
    //     //         transacciones.eliminar(indiceLista);
    //     //     }
    //     // }
    // }

    public void eliminarTransaccion(Transaccion t) {
        int id = t.id();

        // 1. Eliminar del heap en O(log n)
        HandleHeap<Transaccion> h = transaccionesHeap.handleDe(id);
        transaccionesHeap.eliminar(h);

        // 2. Eliminar de la lista doble en O(1)
        Nodo<Transaccion> nodo = handles.obtener(id).nodo();
        transaccionesLista.eliminarNodo(nodo);

        // 3. Eliminar el handle de la lista 'handles' en O(1)
        Nodo<HandleLista<Transaccion>> nodoHandle = handles.obtenerNodo(id); // suponiendo que podés obtener el nodo
        handles.eliminarNodo(nodoHandle); // o eliminar en O(1) si ya lo tenés
    }



    // // Método auxiliar para buscar índice de una transacción en la lista
    // private int buscarIndiceTransaccion(Transaccion t) {
    //     int n = transacciones.longitud();
    //     for (int i = 0; i < n; i++) {
    //         Transaccion actual = transacciones.obtener(i);
    //         if (actual == t) {
    //             return i;
    //         }
    //     }
    //     return -1; // no encontrado
    // }

    public Transaccion[] obtenerTransacciones() {
        Transaccion[] res = new Transaccion[tamañoBloque()];
        for (int i = 0; i < tamañoBloque(); i++) {
            res[i] = handles.obtener(i).valor();
        }
        return res;

        // ListaIterador it = transaccionesLista.iterador();
        // int longitud = transaccionesLista.longitud();
        // Transaccion[] res = new Transaccion[longitud];
        // int i = 0;
        // while (it.haySiguiente()) {
        //     Nodo<Transaccion> t_nodo = (Nodo<Transaccion>) it.siguiente();
        //     res[i++] = t_nodo.valor();
        // }
        // return res;
    }

    public int id() {
        return id;
    }

}
