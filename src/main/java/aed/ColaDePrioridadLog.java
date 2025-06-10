package aed;

import java.util.ArrayList;

public class ColaDePrioridadLog<T extends Comparable<T>> {
    private ArrayList<T> heap;

    public ColaDePrioridadLog() {
        heap = new ArrayList<>();
    }

    // Algoritmo clase teorica cambia si es mayor que el padre
    private void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (heap.get(i).compareTo(heap.get(padre)) > 0) {
                swap(i, padre);
                i = padre;
            } else break;
        }
    }

    // Para desdeSec y O(n)
    private void siftDown(int i) {
        int tam = heap.size();

        while (i < tam) {
            int izq = (2 * i) + 1;
            int der = (2 * i) + 2;
            int mayor = i;

            // Si su hijoIzq existe y es mayor, actualizamos
            if (izq < tam && heap.get(izq).compareTo(heap.get(mayor)) > 0) {
                mayor = izq;
            }
            // Si su hijoDer existe y es mayor, actualizamos
            if (der < tam && heap.get(der).compareTo(heap.get(mayor)) > 0) {
                mayor = der;
            } 
            // Si mayor cambio en algun momento, swap
            if (mayor != i) {
                swap(i, mayor);
                i = mayor; // Cambiamos para seguir bajando y ordenando
            } else break; // Terminamos
        }
    }

    // Intercambia los valores en posicion i y j
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void encolar(T elem) {
        heap.add(elem);
        siftUp(heap.size() - 1);
    }

    public T consultarMax() {
        if (heap.isEmpty()) throw new IllegalStateException("Heap vacío");
        return heap.get(0);
    }

    public int tamaño() {
        return heap.size();
    }

    public void desencolarMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap vacío");
        }

        T maximo = heap.get(0);
        T ultimo = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, ultimo);
            siftDown(0);
        }
    }

    public void colaDePrioridadDesdeSecuencia(ArrayList<T> elementos) {
        heap = new ArrayList<>(elementos);
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

}