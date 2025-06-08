package aed;

public class Berretacoin {

    private Bloque ultimo;
    private int cantidadUsuarios;
    private int[] saldosUsuarios;
    private int maxTenedorId;
    private int maxSaldo;

    public Berretacoin(int n_usuarios){
        this.cantidadUsuarios = n_usuarios;
        this.saldosUsuarios = new int[n_usuarios];
        this.ultimo = null;
        this.maxTenedorId = 1;
        this.maxSaldo = 0;
    }

    public void agregarBloque(Transaccion[] transacciones){
        Bloque nuevoBloque = new Bloque(transacciones);

        if (ultimo == null) {
            ultimo = nuevoBloque;
        }else{
            ultimo.establecerSiguiente(nuevoBloque);
            ultimo = nuevoBloque;
        }

        for (Transaccion transaccion : transacciones) {
            int comprador = transaccion.id_comprador() - 1;
            int vendedor = transaccion.id_vendedor() - 1;
            int monto = transaccion.monto();

            saldosUsuarios[comprador]-= monto;
            saldosUsuarios[vendedor]+=monto;
        }

    }

    public Transaccion txMayorValorUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int montoMedioUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
}
