package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<Bloque> blockchain;
    private MaxHeap<Usuario> usuarios;
    private ArrayList<MaxHeap<Usuario>.Handle> handlesUsuarios;

    // O(p) - crea instancia de berretacoin
    public Berretacoin(int n_usuarios) { 

        // inicializa atributos
        blockchain = new ListaEnlazada<Bloque>();
        ArrayList<Usuario> listaUsuarioss = new ArrayList<>(n_usuarios);

        // O(p) - crea n_usuarios usuarios
        for (int i = 1; i <= n_usuarios; i++) {
            listaUsuarioss.add(new Usuario(i));
        }

        // O(1 + p) - inicializa maxheap y lo llena desde secuencia
        usuarios = new MaxHeap<Usuario>();
        usuarios.maxHeapDesdeSecuencia(listaUsuarioss);

        // O(1 + p) - inicializa handlesusuarios y "espeja" los handles internos del maxheap
        handlesUsuarios = new ArrayList<>(n_usuarios);
        for (int i = 0; i < n_usuarios; i++) {
            handlesUsuarios.add(usuarios.handleDe(i));
        }

    }

    // O(n * log p) - agrega un bloque al blockchain
    public void agregarBloque(Transaccion[] transacciones) {
        
        // O(n + 1) - crea un bloque y lo agrega al blockchain
        Bloque nuevo = new Bloque(blockchain.longitud(), transacciones);
        blockchain.agregarAtras(nuevo);

        // O(n * log p) - ordena maxheap
        for (Transaccion tx : transacciones) {
            if (tx.id_comprador() != 0) {
                // obtiene los usuarios en caso de no ser tx de creacion
                Usuario comprador = usuarios.verElemento(handlesUsuarios.get(tx.id_comprador() - 1)); // O(1)
                Usuario vendedor = usuarios.verElemento(handlesUsuarios.get(tx.id_vendedor() - 1)); // O(1)

                // actualiza su monto a cada uno
                comprador.sumarAMonto(-tx.monto()); // O(1)
                vendedor.sumarAMonto(tx.monto()); // O(1)
                
                // reordena a ambos en maxheap usuarios
                usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1)); // O(log p)
                usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1)); // O(log p)
            } else {
                // obtiene al vendedor en caso de ser tx de creacion
                Usuario vendedor = usuarios.verElemento(handlesUsuarios.get(tx.id_vendedor() - 1)); // O(1)
                
                // actualiza su monto
                vendedor.sumarAMonto(tx.monto()); // O(1)

                // reordena el maxheap usuarios
                usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1)); // O(log p)
            }
        }

    }

    // O(1) - devuelve tx maxima
    public Transaccion txMayorValorUltimoBloque() {
        return blockchain.obtenerUltimo().transaccionMaxima(); // O(1)
    }

    // O(n) - devuelve a lista de transacciones del ultimo bloque
    public Transaccion[] txUltimoBloque() {
        Bloque ultimoBloque = blockchain.obtenerUltimo(); // O(1)
        if (ultimoBloque == null) {
            return new Transaccion[0];
        } else {
            return ultimoBloque.obtenerTransacciones(); // O(n)
        }
    }

    // O(1) - devuelve el usuario con maximo monto
    public int maximoTenedor() {
        return usuarios.consultarMaximo().id(); // O(1)
    }

    // O(1) - devuelve monto medio del ultimo bloque
    public int montoMedioUltimoBloque() {
        return blockchain.obtenerUltimo().montoMedio(); // O(1)
    }

    // O(log n + log p) - elimina mayor tx del ultimo bloque y reordena
    public void hackearTx() {
        Bloque ultimoBloque = blockchain.obtenerUltimo(); // O(1)
        if (ultimoBloque == null)
            return;

        Transaccion tx = ultimoBloque.eliminarTransaccionMaxima(); // O(log n)
        if (tx == null)
            return;
        int monto = tx.monto();
        int idComprador = tx.id_comprador();

        if (idComprador != 0) {
            // si no es de creacion, al comprador:
            Usuario comprador = usuarios.verElemento(handlesUsuarios.get(tx.id_comprador() - 1)); // O(1)

            // O(1 + log p) - actualizo su monto y reordeno
            comprador.sumarAMonto(monto); // O(1)
            usuarios.actualizarPrioridad(handlesUsuarios.get(comprador.id() - 1)); // O(log p)
        }
        // al vendedor:
        Usuario vendedor = usuarios.verElemento(handlesUsuarios.get(tx.id_vendedor() - 1)); // O(1)

        // O(1 + log p) - actualizo su monto y reordeno
        vendedor.sumarAMonto(-monto); // O(1)
        usuarios.actualizarPrioridad(handlesUsuarios.get(vendedor.id() - 1)); // O(log p)
    }

}
