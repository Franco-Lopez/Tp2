package aed;

public class ListaDoblementeEnlazada<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;

    public ListaDoblementeEnlazada() {
        primero = null;
        ultimo = null;
    }

    public int longitud() {
        int res = 0;
        Nodo<T> actual = primero;
        while (actual != null) {
            res++;
            actual = actual.sig();
        }
        return res;
    }

    public void agregarAdelante(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setSig(primero);
            primero.setAnt(nuevo);
            primero = nuevo;
        }
    }

    public Nodo<T> agregarAtras(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (ultimo == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.setSig(nuevo);
            nuevo.setAnt(ultimo);
            ultimo = nuevo;
        }
        return nuevo;
    }

    public T obtener(int i) {
        Nodo<T> actual = primero;
        int contador = 0;
        while (actual != null && contador < i) {
            actual = actual.sig();
            contador++;
        }
        if (actual == null) {
            return null; // índice fuera de rango
        }
        return actual.valor();
    }

    public Nodo<T> obtenerNodo(int i) {
        Nodo<T> actual = primero;
        int contador = 0;
        while (actual != null && contador < i) {
            actual = actual.sig();
            contador++;
        }
        if (actual == null) {
            return null; // índice fuera de rango
        }
        return actual;
    }

    public void eliminar(int i) {
        Nodo<T> actual = primero;
        int contador = 0;
        while (actual != null && contador < i) {
            actual = actual.sig();
            contador++;
        }
        if (actual == null) return; // índice fuera de rango

        eliminarNodo(actual);
    }

    public void eliminarNodo(Nodo<T> nodo) {
        if (nodo == null) return;

        if (nodo.ant() != null) {
            nodo.ant().setSig(nodo.sig());
        } else {
            // nodo es primero
            primero = nodo.sig();
        }

        if (nodo.sig() != null) {
            nodo.sig().setAnt(nodo.ant());
        } else {
            // nodo es ultimo
            ultimo = nodo.ant();
        }

        nodo.setAnt(null);
        nodo.setSig(null);
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo<T> actual = primero;
        int contador = 0;
        while (actual != null && contador < indice) {
            actual = actual.sig();
            contador++;
        }
        if (actual != null) {
            actual.setValor(elem);
        }
    }

    public ListaDoblementeEnlazada(ListaDoblementeEnlazada<T> lista) {
        primero = null;
        ultimo = null;
        Nodo<T> actual = lista.primero;
        while (actual != null) {
            agregarAtras(actual.valor());
            actual = actual.sig();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = primero;
        while (actual != null) {
            sb.append(actual.valor());
            if (actual.sig() != null) sb.append(", ");
            actual = actual.sig();
        }
        sb.append("]");
        return sb.toString();
    }

    public class ListaIterador {
        private Nodo<T> actual;

        public ListaIterador() {
            actual = primero;
        }

        public boolean haySiguiente() {
            return actual != null;
        }

        public T siguiente() {
            if (actual == null) return null;
            T val = actual.valor();
            actual = actual.sig();
            return val;
        }
    }

    public ListaIterador iterador() {
        return new ListaIterador();
    }

    public T obtenerUltimo() {
        return (ultimo == null) ? null : ultimo.valor();
    }

    // Exponer el primero para uso interno (por ejemplo para recorrer)
    public Nodo<T> primero() {
        return primero;
    }
}
