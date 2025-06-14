package aed;

public class ListaEnlazada<T>{
    private Nodo primero;
    private Nodo ultimo;

    private class Nodo {
        T valor;
        Nodo ant;
        Nodo sig;

        Nodo(T v) {
            valor = v;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
    }

    public int longitud() {
        int res = 0;
        if (primero == null) {
            return res;
        } else {
            Nodo actual = primero;
            while (actual != null) {
                res +=1;
                actual = actual.sig;
            }
            return res;
        }
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
           primero = nuevo;
           ultimo = nuevo;
        } else {
            nuevo.ant = null;
            nuevo.sig = primero;
            primero.ant = nuevo;
            primero = nuevo;
        }
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.ant = ultimo;
            nuevo.sig = null;
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
    }

    public T obtener(int i) {
        Nodo actual = primero;
        T res = null;
        for (int contador = 0; contador <= i; contador++) {
            res = actual.valor;
            actual = actual.sig;
        }
        return res;
    }

    public void eliminar(int i) {
        Nodo actual = primero;
        if (this.longitud() > 1) {
            if (i == 0) {
                primero = actual.sig;
                primero.ant = null;
            } else if (i == this.longitud() - 1) {
                ultimo = ultimo.ant;
                ultimo.sig = null;
            } else {
                for (int contador = 0; contador < i; contador++) {
                    actual = actual.sig;
                }
                    (actual.ant).sig = actual.sig;
                    (actual.sig).ant = actual.ant;
                    actual = null;
            }
        } else {
            primero = null;
            ultimo = null;
        }
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for (int contador = 0; contador < indice; contador++) {
            actual = actual.sig;
        }
        actual.valor = elem;
    }
    
    public ListaEnlazada(ListaEnlazada<T> lista) {
        for (int i = 0; i < lista.longitud(); i += 1) {
            T elem = lista.obtener(i);
            this.agregarAtras(elem);
        }
    }
    
    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < this.longitud(); i += 1) {
            res += this.obtener(i);
            if (i < this.longitud() - 1) {
                res += ", ";
            }
        }
        res += "]";
        return res;
    }

    private class ListaIterador {
        int dedito;

        public boolean haySiguiente() {
            return dedito < ListaEnlazada.this.longitud();
        }
        
        public boolean hayAnterior() {
            return dedito > 0;
        }

        public T siguiente() {
            int i = dedito;
            dedito += 1;
            return ListaEnlazada.this.obtener(i);
        }
        

        public T anterior() {
            int i = dedito - 1;
            dedito -= 1;
            return ListaEnlazada.this.obtener(i);
        }
    }

    public ListaIterador iterador() {
        ListaIterador it = new ListaIterador();
        return it;
    }

    public T obtenerUltimo() {
        return ultimo.valor;
    }
}
