import java.util.ArrayList;

/**
 * Esta clase representa un PRODUCTO del inventario.
 * Aquí guardamos toda la información de cada producto.
 */
public class Producto {

    // ===== ATRIBUTOS (lo que tiene un producto) =====
    private String nombre;                  // Nombre del producto
    private double precio;                  // Precio unitario
    private String categoria;               // Categoría (ej: Lácteos, Granos...)
    private String fechaVencimiento;        // Fecha de vencimiento (puede ser null)
    private int cantidad;                   // Cuántas unidades hay
    private ArrayList<String> listaImagenes; // Lista de rutas de imágenes

    /**
     * Constructor: se ejecuta cuando creamos un producto nuevo.
     * Recibe los datos principales y crea la lista de imágenes vacía.
     */
    public Producto(String nombre, double precio, String categoria,
                    String fechaVencimiento, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<>(); // Se inicia vacía
    }

    // ===== GETTERS (para obtener los valores) =====
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    // ===== SETTERS (para modificar los valores) =====
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Agrega una ruta de imagen a la lista de imágenes del producto.
     * Ejemplo de ruta: "imagenes/arroz.jpg"
     */
    public void agregarImagen(String rutaImagen) {
        // Solo agregamos si la ruta no está vacía
        if (rutaImagen != null && !rutaImagen.trim().isEmpty()) {
            listaImagenes.add(rutaImagen.trim());
        }
    }

    /**
     * Calcula el costo total de este producto.
     * Fórmula: precio × cantidad
     */
    public double calcularCostoTotal() {
        return precio * cantidad;
    }

    /**
     * Este método se usa cuando imprimimos el producto.
     * Devuelve un texto bonito con toda la información.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Precio: ₡").append(String.format("%.2f", precio)).append("\n");
        sb.append("Categoría: ").append(categoria).append("\n");
        sb.append("Fecha de Vencimiento: ")
                .append(fechaVencimiento != null ? fechaVencimiento : "No aplica")
                .append("\n");
        sb.append("Cantidad: ").append(cantidad).append("\n");
        sb.append("Costo total: ₡").append(String.format("%.2f", calcularCostoTotal())).append("\n");
        sb.append("Imágenes (").append(listaImagenes.size()).append("): ");

        if (listaImagenes.isEmpty()) {
            sb.append("Ninguna");
        } else {
            sb.append(String.join(", ", listaImagenes));
        }

        sb.append("\n----------------------------------------");
        return sb.toString();
    }
}
