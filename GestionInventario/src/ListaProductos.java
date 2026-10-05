/**
 * Esta clase es la LISTA ENLAZADA SIMPLE.
 * Aquí están todos los métodos para manejar los productos:
 * insertar, modificar, eliminar, mostrar y generar reportes.
 */
public class ListaProductos {

    private Nodo cabeza;  // Apunta al primer nodo de la lista
    private int tamano;   // Cuantos productos hay actualmente

    /**
     * Constructor: crea una lista vacía.
     */
    public ListaProductos() {
        this.cabeza = null;
        this.tamano = 0;
    }

    /**
     * Verifica si la lista está vacía.
     */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Devuelve cuantos productos hay en la lista.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * INSERTAR AL INICIO
     * El nuevo producto se convierte en el primero de la lista.
     */
    public void insertarAlInicio(Producto producto) {
        Nodo nuevo = new Nodo(producto);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        tamano++;
        System.out.println("Producto \"" + producto.getNombre() + "\" insertado al inicio.");
    }

    /**
     * INSERTAR AL FINAL
     * El nuevo producto se agrega al final de la lista.
     */
    public void insertarAlFinal(Producto producto) {
        Nodo nuevo = new Nodo(producto);

        if (estaVacia()) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamano++;
        System.out.println("Producto \"" + producto.getNombre() + "\" insertado al final.");
    }

    /**
     * Busca un producto por su nombre.
     * Devuelve el NODO donde está, o null si no lo encuentra.
     */
    public Nodo buscarPorNombre(String nombre) {
        Nodo actual = cabeza;

        while (actual != null) {
            if (actual.getProducto().getNombre().equalsIgnoreCase(nombre)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * MODIFICAR un producto existente.
     * Solo cambia los campos que el usuario quiera cambiar.
     */
    public boolean modificarProducto(String nombreActual, String nuevoNombre,
                                     double nuevoPrecio, String nuevaCategoria,
                                     String nuevaFecha, int nuevaCantidad) {

        Nodo nodo = buscarPorNombre(nombreActual);

        if (nodo == null) {
            System.out.println("No se encontró el producto \"" + nombreActual + "\".");
            return false;
        }

        Producto p = nodo.getProducto();

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            p.setNombre(nuevoNombre.trim());
        }
        if (nuevoPrecio >= 0) {
            p.setPrecio(nuevoPrecio);
        }
        if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
            p.setCategoria(nuevaCategoria.trim());
        }
        p.setFechaVencimiento(nuevaFecha);
        if (nuevaCantidad >= 0) {
            p.setCantidad(nuevaCantidad);
        }

        System.out.println("Producto modificado correctamente.");
        return true;
    }

    /**
     * Agrega una imagen a un producto que ya existe.
     */
    public boolean agregarImagenAProducto(String nombreProducto, String rutaImagen) {
        Nodo nodo = buscarPorNombre(nombreProducto);

        if (nodo == null) {
            System.out.println("No se encontró el producto \"" + nombreProducto + "\".");
            return false;
        }

        nodo.getProducto().agregarImagen(rutaImagen);
        System.out.println("Imagen agregada al producto \"" + nombreProducto + "\".");
        return true;
    }

    /**
     * ELIMINAR un producto de la lista por su nombre.
     */
    public boolean eliminar(String nombre) {
        if (estaVacia()) {
            System.out.println("La lista esta vacía.");
            return false;
        }

        // Caso especial: el producto está en la cabeza
        if (cabeza.getProducto().getNombre().equalsIgnoreCase(nombre)) {
            cabeza = cabeza.getSiguiente();
            tamano--;
            System.out.println("Producto \"" + nombre + "\" eliminado.");
            return true;
        }

        // Buscamos el nodo anterior al que queremos eliminar
        Nodo actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getProducto().getNombre().equalsIgnoreCase(nombre)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamano--;
                System.out.println("Producto \"" + nombre + "\" eliminado.");
                return true;
            }
            actual = actual.getSiguiente();
        }

        System.out.println("No se encontró el producto \"" + nombre + "\".");
        return false;
    }

    /**
     * Muestra TODOS los productos de la lista.
     */
    public void mostrarTodos() {
        if (estaVacia()) {
            System.out.println("La lista de productos esta vacía.");
            return;
        }

        System.out.println("\n========== LISTA DE PRODUCTOS (" + tamano + ") ==========");
        Nodo actual = cabeza;
        int contador = 1;

        while (actual != null) {
            System.out.println("\nProducto #" + contador);
            System.out.println(actual.getProducto());
            actual = actual.getSiguiente();
            contador++;
        }
        System.out.println("==================================================\n");
    }
    /**
     * MÉTODO ESPECIAL REQUERIDO:
     * Recorre la lista e imprime el costo de cada producto
     * (precio x cantidad) y el costo total de toda la lista.
     */
    public void generarReporteCostos() {
        if (estaVacia()) {
            System.out.println("No hay productos para generar el reporte.");
            return;
        }

        System.out.println("\n========== REPORTE DE COSTOS ==========");
        System.out.printf("%-5s %-25s %10s %10s %15s%n",
                "#", "Producto", "Precio", "Cantidad", "Costo Total");
        System.out.println("---------------------------------------------------------------");

        Nodo actual = cabeza;
        int contador = 1;
        double costoAcumulado = 0.0;

        while (actual != null) {
            Producto p = actual.getProducto();
            double costoProducto = p.calcularCostoTotal();
            costoAcumulado += costoProducto;

            System.out.printf("%-5d %-25s %10.2f %10d %15.2f%n",
                    contador,
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCantidad(),
                    costoProducto);

            actual = actual.getSiguiente();
            contador++;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-52s %15.2f%n", "COSTO TOTAL ACUMULADO:", costoAcumulado);
        System.out.println("=======================================\n");
    }
}