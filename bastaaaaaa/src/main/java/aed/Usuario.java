package aed;

public class Usuario implements Comparable<Usuario> {
    private int id;
    private int monto;

    public Usuario(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public int monto() {
        return monto;
    }

    public void sumarAMonto(int valor) {
        this.monto += valor;
    }

    @Override
    public int compareTo(Usuario u) {
        if (this.monto > u.monto) {
            return 1;
        } else if (this.monto < u.monto) {
            return -1;
        } else {
            if (this.id < u.id) {
                return 1;
            } else if (this.id > u.id) {
                return -1;
            } else {
                return 0; // son el mismo usuario
            }
        }
    }

    // @Override
    // public String toString() {
    //     return String.format("Usuario(id=%d, monto=%d)", id, monto);
    // }
}
