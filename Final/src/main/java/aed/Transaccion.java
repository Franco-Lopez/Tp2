package aed;

import java.util.Objects;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    // O(1) - crea una instancia de transaccion
    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    // O(1)
    @Override
    public int hashCode() {
        return Objects.hash(id, monto, id_comprador(), id_vendedor());
    }

    // O(1) - devuelve id
    public int id() {
        return id;
    }

    // O(1) - devuelve monto
    public int monto() {
        return monto;
    }

    // O(1) - devuelve idcomprador
    public int id_comprador() {
        return id_comprador;
    }

    // O(1) - devuelve idvendedor
    public int id_vendedor() {
        return id_vendedor;
    }

    // O(1) - compara dos tx y los ordena segun:
    @Override
    public int compareTo(Transaccion otro) {
        if (this.monto > otro.monto()) { // gana this por mayor monto o,
            return 1;
        } else if (this.monto < otro.monto()) { // gana otro por mayor monto o,
            return -1;
        } else {
            if (this.id > otro.id()) { // gana this por mayor id o,
                return 1;
            } else if (this.id < otro.id()) { // gana otro por menor id o,
                return -1;
            } else { // son la misma tx
                return 0;
            }
        }
    }

    // O(1) - compara si dos tx son iguales
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Transaccion otra = (Transaccion) obj;
        return id == otra.id &&
                monto == otra.monto &&
                id_comprador == otra.id_comprador &&
                id_vendedor == otra.id_vendedor;
    }

}