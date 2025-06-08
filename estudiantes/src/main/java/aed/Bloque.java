package aed;

public class Bloque {
    private Transaccion[] transacciones;
    private Bloque siguiente;

    public Bloque(Transaccion[] transacciones) {
        this.transacciones = transacciones;
        this.siguiente = null;
    }

    public Transaccion[] obtenerTransacciones() {
        return transacciones;
    }

    public Bloque obtenerSiguiente() {
        return siguiente;
    }

    public void establecerSiguiente(Bloque siguiente) {
        this.siguiente = siguiente;
    }
}