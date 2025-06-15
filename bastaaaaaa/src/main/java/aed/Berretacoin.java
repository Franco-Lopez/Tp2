package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaDoblementeEnlazada<Bloque> blockchain;
    private MaxHeap<Usuario> usuarios;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<HandleHeap<Usuario>> handlesUsuarios;

    public Berretacoin(int n_usuarios) {
        blockchain = new ListaDoblementeEnlazada<>();
        listaUsuarios = new ArrayList<>(n_usuarios);

        for (int i = 1; i <= n_usuarios; i++) {
            listaUsuarios.add(new Usuario(i));
        }

        usuarios = new MaxHeap<>();
        usuarios.maxHeapDesdeSecuencia(listaUsuarios);

        handlesUsuarios = new ArrayList<>(n_usuarios);
        for (int i = 0; i < listaUsuarios.size(); i++) {
            handlesUsuarios.add(usuarios.handleDe(i));
        }
    }

    public void agregarBloque(Transaccion[] transacciones) {
        Bloque nuevo = new Bloque(blockchain.longitud());
        nuevo.agregar(transacciones);
        blockchain.agregarAtras(nuevo);

        for (Transaccion tx : transacciones) {
            System.out.println("Transaccion" + " " + tx.id());
            int idComprador = tx.id_comprador();
            int idVendedor = tx.id_vendedor();
            System.out.println("Monto" + " " + tx.monto());

            Usuario viejoVendedor = listaUsuarios.get(idVendedor - 1);
            Usuario nuevoVendedor = new Usuario(viejoVendedor.id());
            nuevoVendedor.sumarAMonto(viejoVendedor.monto() + tx.monto());
            
            listaUsuarios.set(idVendedor - 1, nuevoVendedor);
            usuarios.actualizarClave(handlesUsuarios.get(idVendedor - 1), nuevoVendedor);
            // Usuario vendedor = listaUsuarios.get(idVendedor - 1);
            // vendedor.sumarAMonto(tx.monto());
            // usuarios.actualizarClave(handlesUsuarios.get(idVendedor - 1), vendedor);


            System.out.println("Vendedor" + " " + idVendedor + " " + "Monto actual" + " " + tx.vendedor().monto());

            if (idComprador != 0) {
                Usuario viejoComprador = listaUsuarios.get(idComprador - 1);
                Usuario nuevoComprador = new Usuario(viejoComprador.id());
                nuevoComprador.sumarAMonto(viejoComprador.monto() - tx.monto());
                listaUsuarios.set(idComprador - 1, nuevoComprador);
                usuarios.actualizarClave((handlesUsuarios.get(idComprador - 1)), nuevoComprador);
                // Usuario comprador = listaUsuarios.get(idComprador - 1);
                // comprador.sumarAMonto(-tx.monto());
                // usuarios.actualizarClave(handlesUsuarios.get(idComprador - 1), comprador);

                System.out.println("Comprador" + " " + idComprador + " " + "Monto actual" + " " + tx.comprador().monto());
            }
            System.out.println("Maximo usuario" + " " + maximoTenedor() + " " + "Monto actual" + " " + listaUsuarios.get(maximoTenedor() - 1).monto());
        }
        
    }

    public Transaccion txMayorValorUltimoBloque() {
        if (blockchain.longitud() == 0) return null;
        return blockchain.obtenerUltimo().transaccionMaxima();
    }

    public Transaccion[] txUltimoBloque() {
        if (blockchain.longitud() == 0) return new Transaccion[0];
        return blockchain.obtenerUltimo().obtenerTransacciones();
    }

    public int maximoTenedor() {
        if (usuarios.tamaño() == 0) return -1;
        return usuarios.consultarMaximo().id();
    }

    public int montoMedioUltimoBloque() {
        if (blockchain.longitud() == 0) return 0;
        return blockchain.obtenerUltimo().montoMedio();
    }

    public void hackearTx() {
        // Bloque ultimo = blockchain.obtenerUltimo();
        // Transaccion maxima = ultimo.transaccionMaxima();

        // Usuario comprador = maxima.comprador();
        // Usuario vendedor = maxima.vendedor();
        // int monto = maxima.monto();

        // ultimo.eliminarTransaccion(maxima);
        // comprador.sumarAMonto(monto);
        // vendedor.sumarAMonto(-monto);



        if (blockchain.longitud() == 0) return;

        Bloque ultimo = blockchain.obtenerUltimo();
        Transaccion mayor = ultimo.transaccionMaxima();

        // // Hacemos que valga cero
        // mayor.setMonto(0);
        // ultimo.actualizarTransaccion(mayor);
        

        // Revertimos efecto anterior en compradores y vendedores
        int idComprador = mayor.id_comprador();
        int idVendedor = mayor.id_vendedor();
        int monto = mayor.monto();

        // ultimo.eliminarTransaccion(mayor);

        Usuario vendedor = listaUsuarios.get(idVendedor - 1);
        vendedor.sumarAMonto(-monto);
        usuarios.actualizarClave(handlesUsuarios.get(idVendedor - 1), vendedor);

        if (idComprador != 0) {
            Usuario comprador = listaUsuarios.get(idComprador - 1);
            comprador.sumarAMonto(monto);
            usuarios.actualizarClave(handlesUsuarios.get(idComprador - 1), comprador);
        }

        ultimo.eliminarTransaccion(mayor);
    }
}

