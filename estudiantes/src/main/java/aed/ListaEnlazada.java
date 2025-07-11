package aed;

public class ListaEnlazada<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    public class Nodo {
        T valor;
        Nodo ant;
        Nodo sig;

        Nodo(T v) {
            valor = v;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud=0;
    }

    public int longitud() {
        return longitud;
    }

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

    public T obtener(int i) {
        Nodo actual = primero;
        for (int contador = 0; contador < i; contador++) {
            if (actual == null)
                return null;
            actual = actual.sig;
        }
        return (actual == null) ? null : actual.valor;
    }

    public Nodo primero() {
        return primero;
    }

    public T obtenerUltimo() {
        return (ultimo == null) ? null : ultimo.valor;
    }
}
