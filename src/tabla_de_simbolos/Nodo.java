package tabla_de_simbolos;

/**
 *
 * @author DavidCQ
 */
public class Nodo {
    private String dato;
    private Nodo siguiente;
    private int hash;

    public Nodo(String dato) {
        this.dato = dato;
        this.siguiente = null;
        this.hash = Hash.algoritmo1(dato);
    }

    public String getDato() {
        return dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public int getHash() {
        return hash;
    }
}
