package aed;

import java.util.ArrayList;

// import main.java.aed.MaxHeap;
// import main.java.aed.Usuario;

public class Berretacoin {
    private ListaEnlazada<Bloque> blockchain;
    private MaxHeap<Usuario> usuarios;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<MaxHeap<Usuario>.Handle> handlesUsuarios;

    public Berretacoin(int n_usuarios){
        blockchain = new ListaEnlazada<Bloque>();
        listaUsuarios = new ArrayList<>(n_usuarios);

        for (int i = 1; i <= n_usuarios; i++) {
            listaUsuarios.add(new Usuario(i));
        }
        usuarios = new MaxHeap<Usuario>();
        usuarios.maxHeapDesdeSecuencia(listaUsuarios);

        handlesUsuarios = new ArrayList<>();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            handlesUsuarios.add(usuarios.handleDe(i));
        }       
    }

    public void agregarBloque(Transaccion[] transacciones){
        Bloque nuevo = new Bloque(blockchain.longitud());
        nuevo.agregar(transacciones);
        blockchain.agregarAtras(nuevo);
        
        // for (Transaccion tx : transacciones) {
        //     Usuario comprador = tx.comprador();
        //     Usuario vendedor = tx.vendedor();
        //     // int monto = tx.monto();

        //     if (comprador.id() != 0) {
        //         // comprador.sumarAMonto(-monto);
        //         usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1));
        //     }
        //     if (vendedor.id() != 0) {
        //         // vendedor.sumarAMonto(monto);
        //         usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
        //     }
        // }

        for (Transaccion tx : transacciones) {
            if (tx.id_comprador() != 0) {
                Usuario comprador = listaUsuarios.get(tx.id_comprador() - 1);
                Usuario vendedor = listaUsuarios.get(tx.id_vendedor() - 1);

                comprador.sumarAMonto(-tx.monto());
                vendedor.sumarAMonto(tx.monto());

                usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1));
                usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
                System.out.print("Transaccion" + tx.id());
                System.out.println("Comprador" + comprador.id() + "=" + comprador.monto());
                System.out.println("Vendedor" + vendedor.id() + "=" + vendedor.monto());
                System.out.println("Maximo" + maximoTenedor());
            } else {
                Usuario vendedor = listaUsuarios.get(tx.id_vendedor() - 1);
                vendedor.sumarAMonto(tx.monto());
                usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
                System.out.print("Transaccion" + tx.id());
                System.out.println("Vendedor" + vendedor.id() + "=" + vendedor.monto());
                System.out.println("Maximo" + maximoTenedor());
            }
        }

    }

    public Transaccion txMayorValorUltimoBloque(){
        return blockchain.obtenerUltimo().transaccionMaxima();
    }

    public Transaccion[] txUltimoBloque(){
        if (blockchain.obtenerUltimo() == null) {
            return null;
        } else {
            return null; //blockchain.obtenerUltimo().obtenerTransacciones();
        }
    }

    public int maximoTenedor(){
        return usuarios.consultarMaximo().id();
    }

    public int montoMedioUltimoBloque(){
        return blockchain.obtenerUltimo().montoMedio();
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
}
