package aed;

public class Usuario implements Comparable<Usuario> {
    private int id;
    private int monto;

    // O(1) - crea instancia de usuario
    public Usuario(int i) {
        id = i;
        monto = 0;
    }

    // O(1) - devuelve id
    public int id() {
        return id;
    }

    // O(1) - devuelve monto
    public int monto() {
        return monto;
    }

    // O(1) - modifica monto (sumando m)
    public void sumarAMonto(int m) {
        monto += m;
    }

    // O(1) - compara si dos usuarios son iguales
    @Override
    public boolean equals(Object otro) {
        if (this == otro)
            return true;
        if (otro == null || this.getClass() != otro.getClass()) {
            return false;
        } else {
            Usuario otro_Usuario = (Usuario) otro;
            return id == otro_Usuario.id;
        }
    }

    // O(1) - compara dos usuarios y las ordena segun:
    @Override
    public int compareTo(Usuario u) {
        if (this.monto > u.monto()) { // gana this por mayor monto o,
            return 1;
        } else if (this.monto < u.monto()) { // gana u por mayor monto o,
            return -1;
        } else { // si son iguales:
            if (this.id < u.id()) { // gana this por menor id o,
                return 1;
            } else if (this.id() > u.id()) { // gana u por menor id o,
                return -1;
            } else {
                return 0; // son el mismo usuario
            }
        }
    }

    // O(1)
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
