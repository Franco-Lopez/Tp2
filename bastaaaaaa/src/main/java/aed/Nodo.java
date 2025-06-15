package aed;

public class Nodo<T> {
    T valor;
    Nodo<T> ant;
    Nodo<T> sig;

    public Nodo(T v) {
        valor = v;
    }

    public T valor() {
        return valor;
    }

    public void setValor(T nuevoValor) {
        valor = nuevoValor;
    }

    public Nodo<T> ant () {
        return ant;
    }

    public Nodo<T> sig() {
        return sig;
    }

    public void setAnt(Nodo<T> nuevoAnt) {
        ant = nuevoAnt;
    }

    public void setSig(Nodo<T> nuevoSig) {
        sig = nuevoSig;
    }

}
