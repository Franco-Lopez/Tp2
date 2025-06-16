package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxHeapTest {

  @Test
    public void testEncolarYConsultarMaximo() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.encolar(10);
        heap.encolar(30);
        heap.encolar(20);

        assertEquals(30, heap.consultarMaximo());
    }

    @Test
    public void testDevolverMaximo() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.encolar(10);
        heap.encolar(30);
        heap.encolar(20);

        assertEquals(30, heap.devolverMaximo());
        assertEquals(20, heap.consultarMaximo());
    }

    @Test
    public void testEliminarMaximo() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.encolar(5);
        heap.encolar(15);
        heap.eliminarMaximo();

        assertEquals(5, heap.consultarMaximo());
    }

    @Test
    public void testActualizarClave() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        MaxHeap<Integer>.Handle h = heap.encolar(10);
        heap.actualizarClave(h, 25);

        assertEquals(25, heap.consultarMaximo());
    }

    @Test
    public void testActualizarPrioridad() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        MaxHeap<Integer>.Handle h = heap.encolar(10);
        heap.actualizarPrioridad(h);

        assertEquals(10, heap.consultarMaximo());
    }

    @Test
    public void testEliminarPorHandle() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        MaxHeap<Integer>.Handle h1 = heap.encolar(10);
        MaxHeap<Integer>.Handle h2 = heap.encolar(20);
        MaxHeap<Integer>.Handle h3 = heap.encolar(15);

        Integer eliminado = heap.eliminar(h2); // elimina el 20
        assertEquals(20, eliminado);
        assertEquals(15, heap.consultarMaximo());
    }

    @Test
    public void testVerElemento() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        MaxHeap<Integer>.Handle h = heap.encolar(42);

        assertEquals(42, heap.verElemento(h));
    }

    @Test
    public void testMaxHeapDesdeSecuencia() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(10);
        lista.add(50);
        lista.add(30);

        heap.maxHeapDesdeSecuencia(lista);

        assertEquals(50, heap.consultarMaximo());
        assertEquals(3, heap.tamaño());
    }

    @Test
    public void testHandleDeYDelMaximo() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.encolar(1);
        heap.encolar(2);
        heap.encolar(3);

        MaxHeap<Integer>.Handle hMax = heap.handleDelMaximo();
        assertEquals(0, hMax.puntero());
        assertEquals(heap.consultarMaximo(), heap.verElemento(hMax));
    }

    @Test
    public void testTamañoYObtenerElementos() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.encolar(1);
        heap.encolar(3);
        heap.encolar(2);

        assertEquals(3, heap.tamaño());
        ArrayList<Integer> elems = heap.obtenerElementos();
        assertTrue(elems.contains(1));
        assertTrue(elems.contains(2));
        assertTrue(elems.contains(3));
    }

}
