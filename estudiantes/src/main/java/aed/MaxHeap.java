package aed;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> {
    private ArrayList<T> heap;
    private int longitud;
    private ArrayList<Handle> handles;

    public MaxHeap() {
        heap = new ArrayList<>();
        longitud = 0;
        handles = new ArrayList<>();
    }

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

    private void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (heap.get(i).compareTo(heap.get(padre)) > 0) {
                swap(i, padre);
                i = padre;
            } else
                break;
        }
    }

    private void siftDown(int i) {
        while (i < longitud) {
            int izq = (2 * i) + 1;
            int der = (2 * i) + 2;
            int mayor = i;

            // Comprueba si existen y si son mayores
            if (izq < longitud && heap.get(izq).compareTo(heap.get(mayor)) > 0) {
                mayor = izq;
            }
            if (der < longitud && heap.get(der).compareTo(heap.get(mayor)) > 0) {
                mayor = der;
            }
            if (mayor != i) { // Si de modifico -> swap
                swap(i, mayor);
                i = mayor; // Seguimos bajando
            } else
                break;
        }
    }

    private void swap(int i, int j) {
        // Intercambio elementos del heap
        T tempElem = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tempElem);

        // Intercambio handles
        Handle tempHandle = handles.get(i);
        handles.set(i, handles.get(j));
        handles.set(j, tempHandle);

        // Actualizo punteros internos
        handles.get(i).moverA(i);
        handles.get(j).moverA(j);
    }

    public Handle encolar(T elem) {
        heap.add(elem);
        longitud += 1;
        int indice = longitud - 1;
        Handle h = new Handle(indice);
        handles.add(h);
        siftUp(indice);
        return h;
    }

    public int tamaño() {
        return longitud;
    }

    public T consultarMaximo() {
        if (longitud == 0) {
            return null;
        }
        return heap.get(0);
    }

    public T devolverMaximo() {
        if (tamaño() == 0) {
            return null;
        }

        T maximo = heap.get(0);
        int indiceUltimo = tamaño() - 1;

        swap(0, indiceUltimo);

        longitud -= 1; // no usar remove

        // heap.remove(indiceUltimo);
        // handles.remove(indiceUltimo); //ALTERNATIVA AL BORRADO INTERCAMBIO EL ULTIMO
        // POR EL INDICE A BORRAR Y DIGO QUE AHORA EL ARRAY ES N-1

        if (tamaño() > 0) {
            siftDown(0);
        }

        return maximo;
    }

    public void eliminarMaximo() {
        if (longitud == 0) {

        }
        int indiceUltimo = tamaño() - 1;

        swap(0, indiceUltimo);

        longitud -= 1;

        if (longitud > 0) {
            siftDown(0);
        }
    }

    public void actualizarClave(Handle h, T nuevaClave) {
        int i = h.puntero();
        T viejo = heap.get(i);
        heap.set(i, nuevaClave);

        if (nuevaClave.compareTo(viejo) > 0) {
            siftUp(i);
        } else if (nuevaClave.compareTo(viejo) < 0) {
            siftDown(i);
        }
    }

    public void actualizarPrioridad(Handle h) {
        int i = h.puntero();
        siftUp(i);
        siftDown(i);
    }

    public T eliminar(Handle h) {
        int i = h.puntero();
        int ultimo = tamaño() - 1;

        if (i >= tamaño()) {
            return null;
        }

        T eliminado = heap.get(i);
        swap(i, ultimo);

        longitud -= 1;

        if (i < tamaño()) {
            siftUp(i);
            siftDown(i);
        }
        return eliminado;
    }

    public T verElemento(Handle h) {
        return heap.get(h.puntero());
    }

    public void maxHeapDesdeSecuencia(ArrayList<T> elementos) {
        heap.clear();
        handles.clear();

        longitud = elementos.size();

        for (int i = 0; i < longitud; i++) {
            heap.add(elementos.get(i));
            handles.add(new Handle(i));
        }

        for (int i = (longitud / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public ArrayList<T> obtenerElementos() {
        return new ArrayList<>(heap); // Es una copia!! no el original
    }

    public Handle handleDe(int i) {
        return handles.get(i);
    }

    public int indiceMaximo() {
        return 0;
    }

    public Handle handleDelMaximo() {
        return handles.get(0);
    }

}
