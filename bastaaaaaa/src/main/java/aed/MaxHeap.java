package aed;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable<T>> {
    private ArrayList<T> heap;
    private ArrayList<HandleHeap<T>> handles;
    private int longitud;

    public MaxHeap() {
        heap = new ArrayList<>();
        handles = new ArrayList<>();
        longitud = 0;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);

        HandleHeap<T> hTemp = handles.get(i);
        handles.set(i, handles.get(j));
        handles.set(j, hTemp);

        handles.get(i).setIndice(i);
        handles.get(j).setIndice(j);
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
        while (true) {
            int izq = 2 * i + 1;
            int der = 2 * i + 2;
            int mayor = i;

            if (izq < longitud && heap.get(izq).compareTo(heap.get(mayor)) > 0)
                mayor = izq;
            if (der < longitud && heap.get(der).compareTo(heap.get(mayor)) > 0)
                mayor = der;

            if (mayor != i) {
                swap(i, mayor);
                i = mayor;
            } else
                break;
        }
    }

    public HandleHeap<T> encolar(T elem) {
        if (longitud < heap.size()) {
            heap.set(longitud, elem);
            HandleHeap<T> h = handles.get(longitud);
            h.setIndice(longitud);
        } else {
            heap.add(elem);
            handles.add(new HandleHeap<>(longitud));
        }
        longitud++;
        siftUp(longitud - 1);
        return handles.get(longitud - 1);
    }

    public T consultarMaximo() {
        if (longitud == 0)
            return null;
        return heap.get(0);
    }

    public T eliminarMaximo() {
        if (longitud == 0)
            return null;

        T max = heap.get(0);
        swap(0, longitud - 1);
        longitud--;
        siftDown(0);

        return max;
    }

    // public void actualizarClave(HandleHeap<T> h, T nuevaClave) {
    //     int i = h.getIndice();
    //     if (i < 0 || i >= longitud)
    //         return;

    //     T viejo = heap.get(i);
    //     int comp = nuevaClave.compareTo(viejo);
    //     heap.set(i, nuevaClave);

    //     if (comp > 0)
    //         siftUp(i);
    //     else if (comp < 0)
    //         siftDown(i);
    // }

    public void actualizarClave(HandleHeap<T> h, T nuevaClave) {
        int i = h.getIndice();
        if (i < 0 || i >= longitud)
            return;

        heap.set(i, nuevaClave);
        siftUp(i);
        siftDown(i);
    }

    public T eliminar(HandleHeap<T> h) {
        int i = h.getIndice();
        if (i < 0 || i >= longitud)
            return null;

        T eliminado = heap.get(i);
        swap(i, longitud - 1);
        longitud--;
        siftUp(i);
        siftDown(i);

        return eliminado;
    }

    public int tamaño() {
        return longitud;
    }

    public ArrayList<T> obtenerElementos() {
        return new ArrayList<>(heap.subList(0, longitud));
    }

    /**
     * Construye el heap desde una secuencia dada en tiempo O(n)
     * 
     * @param secuencia List de elementos a construir el heap
     */
    public void maxHeapDesdeSecuencia(List<T> secuencia) {
        heap.clear();
        handles.clear();
        heap.addAll(secuencia);
        longitud = secuencia.size();

        for (int i = 0; i < longitud; i++) {
            handles.add(new HandleHeap<>(i));
        }

        // Desde el último padre hacia arriba, hacer siftDown
        for (int i = (longitud / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Devuelve el handle asociado al índice i del heap
     * 
     * @param i índice válido [0, tamaño)
     * @return HandleHeap<T> correspondiente
     */
    public HandleHeap<T> handleDe(int i) {
        if (i < 0 || i >= longitud)
            return null;
        return handles.get(i);
    }
}
