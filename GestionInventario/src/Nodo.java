/**
 * Esta clase representa un NODO de la lista enlazada.
 * Cada nodo guarda:
 *  - Un producto
 *  - Una flecha (referencia) al siguiente nodo
 *
 * Imagina una cadena:
 * [Producto1] → [Producto2] → [Producto3] → null
 */
public class Nodo {

    private Producto producto;  // El producto que guarda este nodo
    private Nodo siguiente;     // Apunta al siguiente nodo (o null si es el último)

    /**
     * Constructor: crea un nodo nuevo con un producto.
     * Al inicio, no apunta a nadie (siguiente = null).
     */
    public Nodo(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // ===== GETTERS =====
    public Producto getProducto() {
        return producto;
    }
    public Nodo getSiguiente() {
        return siguiente;
    }

    // ===== SETTERS =====
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
