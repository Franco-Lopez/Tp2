package aed;

public class Bloque {
    private Transaccion[] transacciones;
    private Bloque siguiente;
    private ColaDePrioridadLog<Transaccion> heapTransacciones; 
    private int sumaMontosNoCreacion = 0;
    private int cantidadNoCreacion = 0; 

    public Bloque(Transaccion[] transacciones) {
        this.transacciones = transacciones;
        this.siguiente = null;
        this.heapTransacciones = new ColaDePrioridadLog<>();

        for(Transaccion t: transacciones){
            if(t.id_comprador() != 0){
                heapTransacciones.encolar(t);
                sumaMontosNoCreacion += t.monto();
                cantidadNoCreacion ++;
            }
        }
    }

    public int montoPromedio(){
        if(cantidadNoCreacion == 0){
            return 0;
        }
        return sumaMontosNoCreacion / cantidadNoCreacion;
    }

    public void eliminarUltTransaccion(){
        if(heapTransacciones == null){

        }
        heapTransacciones.desencolarMax();
    }

    public Transaccion transaccionMayorMonto(){
        return heapTransacciones.consultarMax();
    }

    public Transaccion[] obtenerTransacciones() {
        return transacciones;
    }

    public Bloque obtenerSiguiente() {
        return siguiente;
    }

    public void establecerSiguiente(Bloque siguiente) {
        this.siguiente = siguiente;
    }
}