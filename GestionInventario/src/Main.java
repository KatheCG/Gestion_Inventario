import java.util.Scanner;

/**
 * Esta es la clase PRINCIPAL del programa.
 * Aquí está el menú que ve el usuario y el método main().
 */
public class Main {

    // La lista de productos (se comparte en todo el programa)
    private static ListaProductos lista = new ListaProductos();

    // Scanner para leer lo que escribe el usuario
    private static Scanner sc = new Scanner(System.in);

    /**
     * Método main: es el punto de entrada del programa.
     * Se ejecuta automáticamente cuando corres el proyecto.
     */
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  SISTEMA DE GESTIÓN DE INVENTARIOS");
        System.out.println("==============================================");

        menu(); // Llamamos al menú

        sc.close(); // Cerramos el Scanner al final
        System.out.println("\n¡Gracias por usar el sistema! Hasta pronto.");
    }

    /**
     * Menú principal del programa.
     * Se repite hasta que el usuario elija salir (opción 0).
     */
    public static void menu() {
        int opcion;

        do {
            // Dibujamos el menú
            System.out.println("\n");
            System.out.println("    ***** MENÚ PRINCIPAL *****   ");
            System.out.println("1. Insertar producto al inicio");
            System.out.println("2. Insertar producto al final");
            System.out.println("3. Modificar un producto");
            System.out.println("4. Agregar imagen a un producto");
            System.out.println("5. Eliminar un producto");
            System.out.println("6. Mostrar todos los productos");
            System.out.println("7. Generar reporte de costos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(); // Leemos la opción del usuario

            // Según la opción, llamamos al método correspondiente
            switch (opcion) {
                case 1:
                    insertarProducto(true);   // true = al inicio
                    break;
                case 2:
                    insertarProducto(false);  // false = al final
                    break;
                case 3:
                    modificarProducto();
                    break;
                case 4:
                    agregarImagen();
                    break;
                case 5:
                    eliminarProducto();
                    break;
                case 6:
                    lista.mostrarTodos();
                    break;
                case 7:
                    lista.generarReporteCostos();
                    break;
                case 0:
                    // No hacemos nada, solo salimos del ciclo
                    break;
                default:
                    System.out.println("✗ Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0); // Se repite mientras no elijan 0
    }

    /**
     * Pide los datos de un producto nuevo y lo inserta
     * al inicio o al final según el parámetro.
     */
    private static void insertarProducto(boolean alInicio) {
        System.out.println("\n--- Registro de nuevo producto ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("✗ El nombre no puede estar vacío.");
            return;
        }

        // Verificamos que no exista ya un producto con ese nombre
        if (lista.buscarPorNombre(nombre) != null) {
            System.out.println("✗ Ya existe un producto con ese nombre.");
            return;
        }

        System.out.print("Precio: ");
        double precio = leerDouble();
        if (precio < 0) {
            System.out.println("✗ El precio no puede ser negativo.");
            return;
        }

        System.out.print("Categoría: ");
        String categoria = sc.nextLine().trim();
        if (categoria.isEmpty()) {
            System.out.println("✗ La categoría no puede estar vacía.");
            return;
        }

        System.out.print("Fecha de vencimiento (dd/mm/aaaa o Enter si no aplica): ");
        String fecha = sc.nextLine().trim();
        if (fecha.isEmpty()) {
            fecha = null; // No aplica
        }

        System.out.print("Cantidad: ");
        int cantidad = leerEntero();
        if (cantidad < 0) {
            System.out.println("✗ La cantidad no puede ser negativa.");
            return;
        }

        // Creamos el producto con los datos ingresados
        Producto nuevo = new Producto(nombre, precio, categoria, fecha, cantidad);

        // Preguntamos si quiere agregar imágenes ahora
        System.out.print("¿Desea agregar imágenes ahora? (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        while (resp.equals("s") || resp.equals("si") || resp.equals("sí")) {
            System.out.print("Ruta de la imagen (ej: imagenes/producto.jpg): ");
            String ruta = sc.nextLine().trim();
            if (!ruta.isEmpty()) {
                nuevo.agregarImagen(ruta);
            }
            System.out.print("¿Agregar otra imagen? (s/n): ");
            resp = sc.nextLine().trim().toLowerCase();
        }

        // Insertamos según lo que eligió el usuario
        if (alInicio) {
            lista.insertarAlInicio(nuevo);
        } else {
            lista.insertarAlFinal(nuevo);
        }
    }

    /**
     * Permite modificar un producto que ya existe.
     * Versión clara: deja el campo vacío y presiona Enter si no quieres cambiarlo.
     */
    private static void modificarProducto() {
        if (lista.estaVacia()) {
            System.out.println("✗ No hay productos registrados.");
            return;
        }

        System.out.println("\n--- Modificar producto ---");
        System.out.print("Nombre del producto a modificar: ");
        String nombreActual = sc.nextLine().trim();

        if (lista.buscarPorNombre(nombreActual) == null) {
            System.out.println("✗ No se encontró el producto.");
            return;
        }

        System.out.println("\nDeje el campo vacío y presione Enter si NO desea modificarlo.");
        System.out.println("------------------------------------------------");

        // Nombre
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = sc.nextLine().trim();
        if (nuevoNombre.isEmpty()) {
            nuevoNombre = null; // No se cambia
        }

        // Precio
        System.out.print("Nuevo precio: ");
        String precioTexto = sc.nextLine().trim();
        double nuevoPrecio = -1; // -1 significa que no se cambia
        if (!precioTexto.isEmpty()) {
            try {
                nuevoPrecio = Double.parseDouble(precioTexto);
            } catch (NumberFormatException e) {
                System.out.println("✗ Precio inválido. No se modificará el precio.");
                nuevoPrecio = -1;
            }
        }

        // Categoría
        System.out.print("Nueva categoría: ");
        String nuevaCategoria = sc.nextLine().trim();
        if (nuevaCategoria.isEmpty()) {
            nuevaCategoria = null;
        }

        // Fecha de vencimiento
        System.out.print("Nueva fecha de vencimiento (o escriba 'null' para quitarla): ");
        String nuevaFecha = sc.nextLine().trim();
        if (nuevaFecha.isEmpty()) {
            // No se cambia → recuperamos la fecha actual
            nuevaFecha = lista.buscarPorNombre(nombreActual).getProducto().getFechaVencimiento();
        } else if (nuevaFecha.equalsIgnoreCase("null")) {
            nuevaFecha = null;
        }

        // Cantidad
        System.out.print("Nueva cantidad: ");
        String cantidadTexto = sc.nextLine().trim();
        int nuevaCantidad = -1; // -1 significa que no se cambia
        if (!cantidadTexto.isEmpty()) {
            try {
                nuevaCantidad = Integer.parseInt(cantidadTexto);
            } catch (NumberFormatException e) {
                System.out.println("✗ Cantidad inválida. No se modificará la cantidad.");
                nuevaCantidad = -1;
            }
        }

        // Llamamos al método de la lista
        lista.modificarProducto(nombreActual, nuevoNombre, nuevoPrecio,
                nuevaCategoria, nuevaFecha, nuevaCantidad);
    }

    /**
     * Agrega una imagen a un producto existente.
     */
    private static void agregarImagen() {
        if (lista.estaVacia()) {
            System.out.println("✗ No hay productos registrados.");
            return;
        }

        System.out.println("\n--- Agregar imagen a producto ---");
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Ruta de la imagen (ej: imagenes/foto1.jpg): ");
        String ruta = sc.nextLine().trim();

        if (ruta.isEmpty()) {
            System.out.println("✗ La ruta no puede estar vacía.");
            return;
        }

        lista.agregarImagenAProducto(nombre, ruta);
    }

    /**
     * Elimina un producto de la lista.
     */
    private static void eliminarProducto() {
        if (lista.estaVacia()) {
            System.out.println("✗ No hay productos registrados.");
            return;
        }

        System.out.println("\n--- Eliminar producto ---");
        System.out.print("Nombre del producto a eliminar: ");
        String nombre = sc.nextLine().trim();

        lista.eliminar(nombre);
    }

    // ===== MÉTODOS AUXILIARES PARA LEER NÚMEROS DE FORMA SEGURA =====

    /**
     * Lee un número entero. Si el usuario escribe algo incorrecto,
     * le vuelve a pedir el dato.
     */
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("✗ Entrada inválida. Ingrese un número entero: ");
            }
        }
    }

    /**
     * Lee un número decimal (double).
     */
    private static double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("✗ Entrada inválida. Ingrese un número: ");
            }
        }
    }
}
