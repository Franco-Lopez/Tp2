package aed;

public class Usuario implements Comparable<Usuario> {
    private int id;
    private int balance;

    public Usuario(int n) {
        this.id = n;
        this.balance = 0;
    }

    // Luego cambiar la implementacion segun se necesite
    @Override
    public int compareTo(Usuario otro) {
        return this.id - otro.id;
    }

    // Poner bien que sea tipo usuario
    @Override
    public boolean equals(Object a) {
        if (a == null || this.getClass() != a.getClass()) {
            return false;
        } else if (this == a) {
            return true;
        }
        
        Usuario a_usuario = (Usuario) a;
        return this.id == a_usuario.id && this.balance == a_usuario.balance;
    }

    public void actualizarBalance(int monto) {
        this.balance += monto;
    }

    public int balance(Usuario u) {
        return this.balance;
    }
}
