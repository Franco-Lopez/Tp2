package aed;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> {
    private ArrayList<Nodo<T>> heap;
    private int longitud;
    private ArrayList<Handle> handles;

    public class Nodo<T> {
        int referencia;
        T valor;

        // O(1) - crea una instancia de nodo
        public Nodo(int ref, T elem) {
            referencia = ref;
            valor = elem;
        }

        // O(1) - devuelve referencia
        public int ref() {
            return referencia;
        }

        // O(1) - devuelve valor
        public T valor() {
            return valor;
        }

        // O(1) - cambia valor
        public void cambiarValor(T nuevo) {
            valor = nuevo;
        }
    }

    public class Handle {
        private int puntero;

        // O(1) - crea instancia de handle
        public Handle(int i) {
            puntero = i;
        }

        // O(1) - devuelve puntero
        public int puntero() {
            return puntero;
        }

        // O(1) - modificia puntero
        public void moverA(int nuevoPuntero) {
            puntero = nuevoPuntero;
        }
    }

    // O(1) - crea instancia de maxheap vacio
    public MaxHeap() {
        heap = new ArrayList<>();
        longitud = 0;
        handles = new ArrayList<>();
    }

    // O(log n) - sube dependiendo prioridad
    private void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (heap.get(i).valor().compareTo(heap.get(padre).valor()) > 0) {
                swap(i, padre);
                i = padre;
            } else
                break;
        }
    }

    // O(log n) - baja dependiendo prioridad
    private void siftDown(int i) {
        while (i < longitud) {
            int izq = (2 * i) + 1;
            int der = (2 * i) + 2;
            int mayor = i;

            if (izq < longitud && heap.get(izq).valor().compareTo(heap.get(mayor).valor()) > 0) {
                mayor = izq;
            }
            if (der < longitud && heap.get(der).valor().compareTo(heap.get(mayor).valor()) > 0) {
                mayor = der;
            }
            if (mayor != i) {
                swap(i, mayor);
                i = mayor;
            } else
                break;
        }
    }

    // O(1) - intercambia posiciones
    private void swap(int i, int j) {

        // intercambio elementos del heap
        Nodo<T> tempElem = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tempElem);

        // actualizo punteros internos
        handles.get(heap.get(i).ref()).moverA(i);
        handles.get(heap.get(j).ref()).moverA(j);
    }

    // O(log n) - agrega elemento a lo ultimo y sube hasta donde alcance
    public Handle encolar(T elem) {
        int indice = longitud;

        Nodo<T> nodoElem = new Nodo(indice, elem);
        heap.add(nodoElem);

        Handle h = new Handle(indice);
        handles.add(h);

        longitud += 1;

        siftUp(indice);
        return h;
    }

    // O(1) - devuelve longitud
    public int tamaño() {
        return longitud;
    }

    // O(1) - devuelve T maximo
    public T consultarMaximo() {
        if (longitud == 0) {
            return null;
        }
        return heap.get(0).valor();
    }

    // O(log n) - devuelve y elimina el maximo y reordena
    public T devolverMaximo() {
        if (longitud == 0) {
            return null;
        }

        T maximo = consultarMaximo();
        int indiceUltimo = longitud - 1;

        swap(0, indiceUltimo);

        longitud -= 1;

        if (longitud > 0) {
            siftDown(0);
        }

        return maximo;
    }

    // O(log n) - elimina maximo y reordena
    public void eliminarMaximo() {
        if (longitud == 0) {
            return;
        }

        int indiceUltimo = longitud - 1;
        swap(0, indiceUltimo);
        longitud -= 1;

        if (longitud > 0) {
            siftDown(0);
        }
    }

    // O(log n) - cambia valores y reordena
    public void actualizarClave(Handle h, T nuevaClave) {
        int i = h.puntero();
        T viejaClave = heap.get(i).valor();
        heap.get(i).cambiarValor(nuevaClave);

        if (nuevaClave.compareTo(viejaClave) > 0) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    // O(log n) - actualiza el orden
    public void actualizarPrioridad(Handle h) {
        int i = h.puntero();
        siftUp(i);
        siftDown(i);
    }

    // O(log n) - elimina y reordena
    public T eliminar(Handle h) {
        int i = h.puntero();
        int ultimo = longitud - 1;

        if (i >= longitud) {
            return null;
        }

        T eliminado = heap.get(i).valor();
        swap(i, ultimo);

        longitud -= 1;

        if (i < longitud) {
            siftUp(i);
            siftDown(i);
        }
        return eliminado;
    }

    // O(1) - devuelve elemento T
    public T verElemento(Handle h) {
        if (h.puntero() >= longitud) {
            return null;
        } else {
            return heap.get(h.puntero()).valor();
        }
    }

    // O(n) - crea una instancia de maxheap desde una secuencia
    public void maxHeapDesdeSecuencia(ArrayList<T> elementos) {
        longitud = elementos.size();

        for (int i = 0; i < longitud; i++) {
            Nodo<T> nuevo = new Nodo(i, elementos.get(i));
            heap.add(nuevo);
            handles.add(new Handle(i));
        }

        for (int i = (longitud / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    // O(n) - devuelve copia de la lista heap
    public ArrayList<T> obtenerElementos() {
        ArrayList<T> res = new ArrayList<>(longitud);
        for (int i = 0; i < longitud; i++) {
            res.add(heap.get(i).valor());
        }
        return res;
    }

    // O(1) - devuelve el iesimo handle
    public Handle handleDe(int i) {
        if (i >= longitud || i < 0) {
            return null;
        } else {
            return handles.get(heap.get(i).ref());
        }
    }

}
