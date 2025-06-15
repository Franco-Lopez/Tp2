package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private Usuario comprador;
    private Usuario vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.comprador = new Usuario(id_comprador);
        this.vendedor = new Usuario(id_vendedor);
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        if (this.monto > otro.monto()) {
            return 1;
        } else if (this.monto < otro.monto()) {
            return -1;
        } else {
            if (this.id > otro.id()) {
                return 1;
            } else if (this.id < otro.id()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null || this.getClass() != otro.getClass()) {
            return false;
        } else {
            Transaccion otro_Usuario = (Transaccion) otro;
            return compareTo(otro_Usuario) == 0;
        }
    }

    public int id() {
        return id;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return comprador.id();
    }
    
    public int id_vendedor() {
        return vendedor.id();
    }

    public Usuario comprador() {
        return comprador;
    }
    
    public Usuario vendedor() {
        return vendedor;
    }

    public void setMonto(int nuevoMonto) {
        this.monto = nuevoMonto;
    }

    public void modificar(Transaccion t) {
        this.id = t.id;
        this.comprador = t.comprador;
        this.vendedor = t.vendedor;
        this.monto = t.monto;
    }

    public boolean esDeCreacion() {
        return this.comprador.id() == 0;
    }

}