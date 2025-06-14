package aed;

import java.util.ArrayList;
import java.util.Arrays;

import aed.Transaccion;
import aed.Usuario;

public class Bloque {
    private int id;
    private MaxHeap<Transaccion> movimientos;
    private int montoMedio;
    private ListaEnlazada<Transaccion> transacciones;
    private ArrayList<Handle> hTransacciones;
    // private Transaccion[] transaccionesOrig; //Lista enlazada que se borre en
    // o(1) con una referencia directa al nodo al momento de querer borrar. Asi
    // mantengo el orden relativo para devolver en O(n) la lista de tx x id

    public class Handle {
        private int puntero;

        public Handle(int i) {
            puntero = i;
        }

        public int puntero() {
            return puntero;
        }

        public void moverA(int nuevoPuntero) {
            puntero = nuevoPuntero;
        }
    }

    public Bloque(int id) {
       this.id = id;
       movimientos = new MaxHeap<Transaccion>();
       montoMedio = 0;
       transacciones = new ListaEnlazada<>();
       hTransacciones = new ArrayList<>();
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

    public int montoMedio() {
        return montoMedio;
    }

    public int tamañoBloque() {
        return movimientos.tamaño();
    }

    public ArrayList<Transaccion> movimientosArray() {
        return movimientos.obtenerElementos();
    }

    // public Transaccion[] obtenerTransacciones() {
    //     ArrayList<Transaccion> activas = new ArrayList<>();
    //     for (Transaccion t : transaccionesOrig) {
    //         if (t.estaActiva()) {
    //             activas.add(t);
    //         }
    //     }
    //     return activas.toArray(new Transaccion[0]);
    // }

}
