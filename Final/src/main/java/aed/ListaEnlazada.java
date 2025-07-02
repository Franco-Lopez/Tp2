package aed;

public class ListaEnlazada<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    public class Nodo {
        T valor;
        Nodo ant;
        Nodo sig;

        // O(1) - crea una instancia de nodo
        Nodo(T v) {
            valor = v;
        }
    }

    // O(1) - crea una instancia de listaenlazada
    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    // O(1) - devuelve longitud
    public int longitud() {
        return longitud;
    }

    // O(1) - agrega un nodo al final
    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.ant = ultimo;
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
        longitud++;
    }

    // O(1) - agrega un nodo al final
    public Nodo agregarAtrasConNodo(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.ant = ultimo;
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
        longitud++;
        return nuevo;
    }

    // O(1) - elimina un nodo
    public void eliminarNodo(Nodo nodo) {
        if (nodo == null)
            return;

        if (nodo == primero)
            primero = nodo.sig;
        if (nodo == ultimo)
            ultimo = nodo.ant;

        if (nodo.ant != null)
            nodo.ant.sig = nodo.sig;
        if (nodo.sig != null)
            nodo.sig.ant = nodo.ant;

        nodo.ant = null;
        nodo.sig = null;
        longitud--;
    }

    // O(n) - devuelve el iesimo T
    public T obtener(int i) {
        Nodo actual = primero;
        for (int contador = 0; contador < i; contador++) {
            if (actual == null)
                return null;
            actual = actual.sig;
        }
        return (actual == null) ? null : actual.valor;
    }

    // O(1) - devuelve el primer nodo
    public Nodo primero() {
        return primero;
    }

    // O(1) - devuelve el ultimo T
    public T obtenerUltimo() {
        return (ultimo == null) ? null : ultimo.valor;
    }

}
