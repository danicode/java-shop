package src.main.ui;

import src.main.domain.producto.Producto;
import src.main.domain.producto.ProductoCantidad;
import src.main.domain.producto.tipo.Bebida;
import src.main.domain.producto.tipo.Envasado;
import src.main.domain.producto.tipo.Limpieza;
import src.main.domain.resultado.ResultadoValidacion;
import src.main.domain.tienda.Tienda;
import src.main.service.TiendaService;
import src.main.service.resultado.ResultadoOperacion;

import java.time.LocalDate;
import java.util.List;

public class TiendaUI {

    private final TiendaService tiendaService;

    public TiendaUI() {
        this.tiendaService = new TiendaService();
    }

    public void casoCompraProductos() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 1000, 10000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();
            mostrarTituloTienda();
            System.out.println(tienda.toString());
            mostrarStock(tienda, false);

            // creacion de productos para la compra
            ProductoCantidad aceite = new ProductoCantidad(
                    new Envasado("AB001", "Aceite", 20.0, 0, 15.0, true, LocalDate.now().plusMonths(6), 100.0, Envasado.TipoEnvase.BOTELLA, false, 10.0),
                    10
            );
            ProductoCantidad vino = new ProductoCantidad(
                    new Bebida("AC001", "Vino", 50.0, 0, 15.0, true, LocalDate.now().plusMonths(12), 200.0, 5.0, false, 5.0),
                    5
            );
            ProductoCantidad detergente = new ProductoCantidad(
                    new Limpieza("AZ001", "Detergente", 10.0, 0, 20.0, true, Limpieza.TipoAplicacion.COCINA, 15.0),
                    10
            );

            ResultadoOperacion<Tienda> resultado = tiendaService.comprar(tienda, List.of(aceite, vino, detergente));

            mostrarResultadoOperacion(resultado);
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoComprarProdcutosSaldoInsuficiente() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 1000, 1000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();
            mostrarTituloTienda();
            System.out.println(tienda.toString());
            mostrarStock(tienda, false);

            // creacion de productos para la compra
            ProductoCantidad arroz = new ProductoCantidad(
                    new Envasado("AB002", "Arroz", 15.0, 0, 15.0, true, LocalDate.now().plusMonths(6), 300.0, Envasado.TipoEnvase.CAJA, true, 12.0),
                    100
            );

            ResultadoOperacion<Tienda> resultado = tiendaService.comprar(tienda, List.of(arroz));

            mostrarResultadoOperacion(resultado);
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoComprarProductoMaxStock() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 40, 1000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();
            mostrarTituloTienda();
            System.out.println(tienda.toString());
            mostrarStock(tienda, false);

            // creacion de productos para la compra
            ProductoCantidad jabon = new ProductoCantidad(
                    new Limpieza("AZ002", "Jabón", 5.0, 0, 12.0, true, Limpieza.TipoAplicacion.ROPA, 10.0),
                    50
            );

            ResultadoOperacion<Tienda> resultado = tiendaService.comprar(tienda, List.of(jabon));

            mostrarResultadoOperacion(resultado);
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoVentaProductos() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 1000, 10000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();

            // creacion de productos para la compra
            Producto aceite = new Envasado("AB001", "Aceite", 20.0, 0, 15.0, true, LocalDate.now().plusMonths(6), 100.0, Envasado.TipoEnvase.BOTELLA, false, 10.0);
            ProductoCantidad aceiteCompra = new ProductoCantidad(
                    aceite,
                    10
            );
            Producto vino = new Bebida("AC001", "Vino", 50.0, 0, 15.0, true, LocalDate.now().plusMonths(12), 200.0, 5.0, false, 5.0);
            ProductoCantidad vinoCompra = new ProductoCantidad(
                    vino,
                    5
            );

            ResultadoOperacion<Tienda> resultadoCompra = tiendaService.comprar(tienda, List.of(aceiteCompra, vinoCompra));
            tienda = resultadoCompra.getObjeto();

            mostrarTituloTienda();
            System.out.println(tienda.toString());
            mostrarStock(tienda, false);

            // creacion de productos para la venta
            ProductoCantidad aceiteVenta = new ProductoCantidad(
                    aceite,
                    5
            );
            ProductoCantidad vinoVenta = new ProductoCantidad(
                    vino,
                    3
            );

            ResultadoOperacion<Tienda> resultadoVenta = tiendaService.vender(tienda, List.of(aceiteVenta, vinoVenta));
            mostrarResultadoOperacion(resultadoVenta);
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoVentaMasUnidadesQueDisponibles() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 1000, 10000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();

            // creacion de productos para la compra
            Producto detergente = new Limpieza("AZ001", "Detergente", 10.0, 0, 20.0, true, Limpieza.TipoAplicacion.COCINA, 15.0);
            ProductoCantidad detergenteCompra = new ProductoCantidad(
                    detergente,
                    10
            );

            ResultadoOperacion<Tienda> resultadoCompra = tiendaService.comprar(tienda, List.of(detergenteCompra));
            tienda = resultadoCompra.getObjeto();
            mostrarTituloTienda();
            System.out.println(tienda.toString());
            mostrarStock(tienda, false);

            // creacion de productos para la venta
            ProductoCantidad detergenteVenta = new ProductoCantidad(
                    detergente,
                    40
            );
            ResultadoOperacion<Tienda> resultadoVenta = tiendaService.vender(tienda, List.of(detergenteVenta));
            mostrarResultadoOperacion(resultadoVenta);
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoVentaProductoNoDisponible() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 1000, 10000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();

            // creacion de productos para la compra
            Producto cereal = new Envasado("AB003", "Cereal", 8.0, 20, 10.0, false, LocalDate.now().plusMonths(3), 150.0, Envasado.TipoEnvase.CAJA, true, 8.0);
            ProductoCantidad cerealCompra = new ProductoCantidad(
                    cereal,
                    2
            );

            ResultadoOperacion<Tienda> resultadoCompra = tiendaService.comprar(tienda, List.of(cerealCompra));
            tienda = resultadoCompra.getObjeto();
            tienda = tiendaService.ponerNoDisponibleProducto(tienda, cereal);
            mostrarTituloTienda();
            System.out.println(tienda.toString());
            mostrarStock(tienda, false);
            mostrarStock(tienda, true);

            // creacion de productos para la venta
            ProductoCantidad cerealVenta = new ProductoCantidad(
                    cereal,
                    2
            );
            ResultadoOperacion<Tienda> resultadoVenta = tiendaService.vender(tienda, List.of(cerealVenta));
            mostrarResultadoOperacion(resultadoVenta);
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoListarProductosComestiblesConMenorDescuento() {

        ResultadoValidacion<Tienda> resultadoValidacion = tiendaService.crear("Cuchuflito", 1000, 10000.0);
        if (resultadoValidacion.isValido()) {
            Tienda tienda = resultadoValidacion.getObjeto();
            mostrarTituloTienda();
            System.out.println(tienda.toString());

            // creacion de productos para la compra
            ProductoCantidad aceite = new ProductoCantidad(
                    new Envasado("AB001", "Aceite", 20.0, 100, 15.0, true, LocalDate.now().plusMonths(6), 100.0, Envasado.TipoEnvase.BOTELLA, false, 10.0),
                    10
            );
            ProductoCantidad vino = new ProductoCantidad(
                    new Bebida("AC001", "Vino", 50.0, 50, 15.0, true, LocalDate.now().plusMonths(12), 200.0, 5.0, false, 5.0),
                    5
            );
            ProductoCantidad detergente = new ProductoCantidad(
                    new Limpieza("AZ001", "Detergente", 10.0, 30, 20.0, true, Limpieza.TipoAplicacion.COCINA, 15.0),
                    10
            );
            ProductoCantidad arroz = new ProductoCantidad(
                    new Envasado("AB002", "Arroz", 15.0, 200, 15.0, true, LocalDate.now().plusMonths(6), 300.0, Envasado.TipoEnvase.CAJA, true, 12.0),
                    100
            );
            ProductoCantidad jabon = new ProductoCantidad(
                    new Limpieza("AZ002", "Jabón", 5.0, 50, 12.0, true, Limpieza.TipoAplicacion.ROPA, 10.0),
                    50
            );

            ResultadoOperacion<Tienda> resultado = tiendaService.comprar(tienda, List.of(aceite, vino, detergente, arroz, jabon));

            System.out.println();
            System.out.println("--------------------------------------------------");
            System.out.println("Stock:");
            System.out.println("--------------------------------------------------");
            System.out.println(resultado.getObjeto().mostrarProductos(true));
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Productos comestibles no importados cuyo descuento sea menor a 12.0");
            System.out.println("----------------------------------------------------------------------");

            System.out.println(tienda.obtenerComestiblesConMenorDescuento(12.0));
        } else {
            System.out.println("Errores en la creación de la Tienda:");
            resultadoValidacion.getErrores().forEach(System.out::println);
        }
    }

    public void casoGenerarProductoLimpiezaIdInvalido() {
        Limpieza detergente = new Limpieza("A001", "Detergente", -10.0, null, 20.0, true, Limpieza.TipoAplicacion.COCINA, 15.0);
        ResultadoValidacion<Limpieza> resultadoValidacion = detergente.validar();
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("Valor ingresado");
        System.out.println("--------------------------------------------------");
        System.out.println(detergente);
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("Error al generar producto de limpieza");
        System.out.println("--------------------------------------------------");
        resultadoValidacion.getErrores().forEach(System.out::println);
    }

    private void mostrarTituloTienda() {
        System.out.println("--------------------------------------------------");
        System.out.println("Datos de Tienda");
        System.out.println("--------------------------------------------------");
    }

    private void mostrarStock(Tienda tienda, boolean completo) {
        System.out.println();
        System.out.println("--------------------------------------------------");

        String titulo = completo ? "STOCK COMPLETO" : "STOCK DISPONIBLE PARA VENTA";
        String mensajeSinStock = completo ? "SIN STOCK" : "SIN STOCK DISPONIBLE PARA VENTA";
        String productos = tienda.mostrarProductos(!completo);

        if (productos.contains("No hay productos")) {
            System.out.println(mensajeSinStock);
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println(titulo);
            System.out.println("--------------------------------------------------");
            System.out.println(productos);
        }

        System.out.println();
    }

    private void mostrarResultadoOperacion(ResultadoOperacion<Tienda> resultado) {
        System.out.println();
        if (resultado.isExito()) {
            System.out.println("--------------------------------------------------");
            System.out.println("Operación realizada");
            System.out.println("--------------------------------------------------");
            System.out.println();
            resultado.getDetalleOperacion().forEach(System.out::println);
            System.out.println();
            mostrarStock(resultado.getObjeto(), false);
            System.out.println();
            System.out.println("--------------------------------------------------");
            System.out.println("SALDO EN CAJA: " + resultado.getObjeto().getSaldoEnCaja());
            System.out.println("--------------------------------------------------");
            System.out.println();
            if (!resultado.getMensajes().isEmpty()) {
                System.out.println("--------------------------------------------------");
                System.out.println("Observaciones");
                System.out.println("--------------------------------------------------");
                resultado.getMensajes().forEach(System.out::println);
                System.out.println();
            }
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Operación no concretada");
            System.out.println("--------------------------------------------------");
            resultado.getDetalleOperacion().forEach(System.out::println);
            System.out.println();
            System.out.println("--------------------------------------------------");
            System.out.println("Error en la operación");
            System.out.println("--------------------------------------------------");
            resultado.getMensajes().forEach(System.out::println);
        }
    }
}
