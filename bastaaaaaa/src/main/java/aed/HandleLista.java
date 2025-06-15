package aed;

public class HandleLista<T> {
    private Nodo<T> puntero;

    public HandleLista(Nodo<T> nodo) {
        this.puntero = nodo;
    }

    public Nodo<T> nodo() {
        return puntero;
    }

    public T valor() {
        return puntero.valor();
    }
}

