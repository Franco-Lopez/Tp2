package aed;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private int monto;

    public Usuario(int i) {
        id = i;
        monto = 0;
    }

    public int id() {
        return id;
    }

    public int monto() {
        return monto;
    }

    public void sumarAMonto(int m) {
        monto += m;
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null || this.getClass() != otro.getClass()) {
            return false;
        } else {
            Usuario otro_Usuario = (Usuario) otro;
            return compareTo(otro_Usuario) == 0;
        }
    }

    @Override
    public int compareTo(Usuario u) {
        if (this.monto > u.monto()) {
            return 1;
        } else if (this.monto < u.monto()) {
            return -1;
        } else {
            if (this.id < u.id()) {
                return 1;
            } else if (this.id() > u.id()) {
                return -1;
            } else {
                return 0; //son el mismo usuario
            }
        }
    }


}
