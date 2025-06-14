package aed;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private int monto;

    // Crea una instancia de usuario con un id
    public Usuario(int i) {
        id = i;
        monto = 0;
    }

    // Devuelve su id
    public int id() {
        return id;
    }

    // Devuelve su monto acutal
    public int monto() {
        return monto;
    }

    // Suma el valor pasado a su monto
    public void sumarAMonto(int m) {
        monto += m;
    }

    // Devuelve si dos instancias son las mismas
    @Override
    public boolean equals(Object otro) {
        if (otro == null || this.getClass() != otro.getClass()) {
            return false;
        } else {
            Usuario otro_Usuario = (Usuario) otro;
            return compareTo(otro_Usuario) == 0;
        }
    }

    // Prioridad de orden: monto mayor, en empate, id menor
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
                return 0; // Caso en el que son el mismo usuario
            }
        }
    }


}
