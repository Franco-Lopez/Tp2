package aed;

import java.util.ArrayList;

// import main.java.aed.MaxHeap;
// import main.java.aed.Usuario;

public class Berretacoin {
    private ListaEnlazada<Bloque> blockchain;
    private MaxHeap<Usuario> usuarios;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<MaxHeap<Usuario>.Handle> handlesUsuarios;

    public Berretacoin(int n_usuarios) { // se construye el heap de usuarios con maxHeapDesdeSecuencia, que es O(P).
        blockchain = new ListaEnlazada<Bloque>();
        listaUsuarios = new ArrayList<>(n_usuarios);

        for (int i = 1; i <= n_usuarios; i++) {
            listaUsuarios.add(new Usuario(i));
        }

        usuarios = new MaxHeap<Usuario>();
        usuarios.maxHeapDesdeSecuencia(listaUsuarios);

        handlesUsuarios = new ArrayList<>(listaUsuarios.size());
        for (int i = 0; i < listaUsuarios.size(); i++) {
            handlesUsuarios.add(null); // placeholder
        }

        for (int i = 0; i < usuarios.tamaño(); i++) {
            MaxHeap<Usuario>.Handle h = usuarios.handleDe(i);
            Usuario u = usuarios.verElemento(h);
            handlesUsuarios.set(u.id() - 1, h);
        }

    }

    public void agregarBloque(Transaccion[] transacciones) { // recorro las transacciones en O(n_b), actualizo montos en
                                                             // O(1) y actualizarPrioridad en O(log P) entonces:
                                                             // O(n_b × log P).
        Bloque nuevo = new Bloque(blockchain.longitud(), transacciones);
        blockchain.agregarAtras(nuevo);

        // for (Transaccion tx : transacciones) {
        // Usuario comprador = tx.comprador();
        // Usuario vendedor = tx.vendedor();
        // // int monto = tx.monto();

        // if (comprador.id() != 0) {
        // // comprador.sumarAMonto(-monto);
        // usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1));
        // }
        // if (vendedor.id() != 0) {
        // // vendedor.sumarAMonto(monto);
        // usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
        // }
        // }

        for (Transaccion tx : transacciones) {
            if (tx.id_comprador() != 0) {
                Usuario comprador = listaUsuarios.get(tx.id_comprador() - 1);
                Usuario vendedor = listaUsuarios.get(tx.id_vendedor() - 1);

                comprador.sumarAMonto(-tx.monto());
                vendedor.sumarAMonto(tx.monto());

                usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1));
                usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
            } else {
                Usuario vendedor = listaUsuarios.get(tx.id_vendedor() - 1);
                vendedor.sumarAMonto(tx.monto());
                usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
            }
        }

    }

    public Transaccion txMayorValorUltimoBloque() { // consultarMaximo() en MaxHeap es O(1)
        return blockchain.obtenerUltimo().transaccionMaxima();
    }

    public Transaccion[] txUltimoBloque() { // recorro la lista enlazada de nodos activos una vez
        Bloque ultimoBloque = blockchain.obtenerUltimo();
        if (ultimoBloque == null) {
            return new Transaccion[0];
        } else {
            return ultimoBloque.obtenerTransacciones(); // recorre todos los nodos activos pero los invactivos son nodos
                                                        // null
        } // se ajustan los puntero y ese nodo ya no esta enlazado, no va a aparecer en
          // actual.sig
          // luego recorrer la lista es O(n_b)
    }

    public int maximoTenedor() { // accedo a consultarMaximo() del heap de usuarios.
        return usuarios.consultarMaximo().id();
    }

    public int montoMedioUltimoBloque() { // llamado a devolver atributo privado de bloque
        return blockchain.obtenerUltimo().montoMedio();
    }

    public void hackearTx() { // hago devolverMaximo() en O(log n_b)) + actualización de saldos y
                              // actualizarPrioridad() ×2 (O(log P)).
        Bloque ultimoBloque = blockchain.obtenerUltimo();
        if (ultimoBloque == null)
            return;

        Transaccion tx = ultimoBloque.eliminarTransaccionMaxima();
        if (tx == null)
            return;

        int monto = tx.monto();
        int idComprador = tx.id_comprador();
        int idVendedor = tx.id_vendedor();

        if (idComprador != 0) {
            Usuario comprador = listaUsuarios.get(idComprador - 1);
            comprador.sumarAMonto(monto); // deshacer la resta original
            usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1));
        }

        Usuario vendedor = listaUsuarios.get(idVendedor - 1);
        vendedor.sumarAMonto(-monto); // deshacer la suma original
        usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1));
    }

}
