package src.main;

import src.main.ui.TiendaUI;
import java.util.Scanner;

public class TestTienda {

    private static Scanner scanner;

    public static void main(String [] args) {
        boolean continuar = true;
        TiendaUI tiendaUI = new TiendaUI();
        scanner = new Scanner(System.in);

        do {
            System.out.println("Menú principal");
            System.out.println("__________________________\n");
            System.out.println("1. Compra de productos válidos");
            System.out.println("2. Compra de productos con saldo insuficiente");
            System.out.println("3. Intentar comprar un producto cuando se alcanzó el máximo de stock");
            System.out.println("4. Venta de productos válidos");
            System.out.println("5. Intentar vender más unidades de un producto que las disponibles en stock");
            System.out.println("6. Intentar vender un producto no disponible para la venta");
            System.out.println("7. Obtener lista de productos comestibles con menor descuento");
            System.out.println("8. Generar un producto de limpieza con id inválido");
            System.out.println("9. Salir\n");

            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("Opción seleccionada: 1. Compra de productos válidos\n");
                        tiendaUI.casoCompraProductos();
                        msjRegresarMenu();
                        break;
                    case 2:
                        System.out.println("Opción seleccionada: 2. Compra de productos con saldo insuficiente\n");
                        tiendaUI.casoComprarProdcutosSaldoInsuficiente();
                        msjRegresarMenu();
                        break;
                    case 3:
                        System.out.println("Opción seleccionada: 3. Intentar comprar un producto cuando se alcanzó el máximo de stock\n");
                        tiendaUI.casoComprarProductoMaxStock();
                        msjRegresarMenu();
                        break;
                    case 4:
                        System.out.println("Opción seleccionada: 4. Venta de productos válidos");
                        tiendaUI.casoVentaProductos();
                        msjRegresarMenu();
                        break;
                    case 5:
                        System.out.println("Opción seleccionada: 5. Intentar vender más unidades de un producto que las disponibles en stock");
                        tiendaUI.casoVentaMasUnidadesQueDisponibles();
                        msjRegresarMenu();
                        break;
                    case 6:
                        System.out.println("Opción seleccionada: 6. Intentar vender un producto no disponible para la venta");
                        tiendaUI.casoVentaProductoNoDisponible();
                        msjRegresarMenu();
                        break;
                    case 7:
                        System.out.println("Opción seleccionada: 7. Lista productos comestibles no importados menores a un descuento");
                        tiendaUI.casoListarProductosComestiblesConMenorDescuento();
                        msjRegresarMenu();
                        break;
                    case 8:
                        System.out.println("Opción seleccionada: 8. Generar un producto de limpieza con id inválido");
                        tiendaUI.casoGenerarProductoLimpiezaIdInvalido();
                        msjRegresarMenu();
                        break;
                    case 9:
                        continuar = false;
                        System.out.println("\nMuchas gracias por realizar las pruebas. Espero sus feedbacks!!! ^_^.");
                        break;
                    default:
                        System.out.println("\nIngreso una opción no válida.");
                        msjRegresarMenu();
                        break;
                }
            } else {
                System.out.println("\nIngreso un carácter o no ingreso nada. Debe seleccionar una opción del menú para realizar una acción.");
                scanner.nextLine();
            }
            System.out.println();

        } while (continuar);

        scanner.close();
    }

    private static void msjRegresarMenu() {
        System.out.println("\nPara regresar al menú principal presione Enter");
        scanner.nextLine();
    }
}
