package src.main.service;

import src.main.domain.producto.Producto;
import src.main.domain.producto.ProductoCantidad;
import src.main.domain.resultado.ResultadoValidacion;
import src.main.domain.tienda.Tienda;
import src.main.service.resultado.ResultadoOperacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TiendaService implements TiendaOperaciones {

    public ResultadoValidacion<Tienda> crear(String nombre, Integer maximoStock, Double saldoEnCaja) {
        Tienda tienda = new Tienda(nombre, maximoStock, saldoEnCaja);
        return tienda.validar();
    }

    @Override
    public ResultadoOperacion<Tienda> comprar(Tienda tienda, List<ProductoCantidad> productosConCantidades) {
        double costoTotal = calcularCostoTotal(productosConCantidades);
        int totalCantidad = calcularCantidadTotal(productosConCantidades);
        List<String> mensajes = new ArrayList<>();
        List<String> detalleOperacion = generarDetalleOperacion(productosConCantidades, costoTotal, "COMPRA");

        if (!validarSaldo(tienda, costoTotal, mensajes)) {
            return new ResultadoOperacion<>(tienda, false, costoTotal, mensajes, detalleOperacion);
        }

        if (!validarStock(tienda, totalCantidad, mensajes)) {
            return new ResultadoOperacion<>(tienda, false, costoTotal, mensajes, detalleOperacion);
        }

        tienda.setSaldoEnCaja(tienda.getSaldoEnCaja() - costoTotal);

        for (ProductoCantidad productoCantidad : productosConCantidades) {
            productoCantidad.getProducto().setCantidadEnStock(productoCantidad.getCantidad());
            productoCantidad.getProducto().setDisponibleParaVenta(true);
            tienda.agregarProducto(productoCantidad.getProducto());
        }

        tienda = actualizarStock(tienda, productosConCantidades);

        return new ResultadoOperacion<>(tienda, true, costoTotal, mensajes, detalleOperacion);
    }

    @Override
    public ResultadoOperacion<Tienda> vender(Tienda tienda, List<ProductoCantidad> productosConCantidades) {
        double totalVenta = 0.0;
        List<String> mensajes = new ArrayList<>();
        List<String> detalleOperacion = new ArrayList<>();

        if (!validarCantidadProductos(productosConCantidades.size(), mensajes)) {
            return new ResultadoOperacion<>(tienda, false, totalVenta, mensajes, detalleOperacion);
        }

        for (ProductoCantidad productoCantidad : productosConCantidades) {
            totalVenta += procesarVentaProducto(tienda, productoCantidad, mensajes, detalleOperacion);
        }

        if (detalleOperacion.isEmpty()) {
            return new ResultadoOperacion<>(tienda, false, totalVenta, mensajes, detalleOperacion);
        }

        tienda = actualizarStock(tienda, productosConCantidades);

        tienda.setSaldoEnCaja(tienda.getSaldoEnCaja() + totalVenta);
        detalleOperacion.add("----------------------------");
        detalleOperacion.add("TOTAL VENTA: " + totalVenta);

        return new ResultadoOperacion<>(tienda, true, totalVenta, mensajes, detalleOperacion);
    }

    @Override
    public Tienda actualizarStock(Tienda tienda, List<ProductoCantidad> productosActualizados) {
        for (ProductoCantidad productoCantidad : productosActualizados) {
            for (Producto productoTienda : tienda.getProductosEnStock()) {
                if (productoTienda.equals(productoCantidad.getProducto())) {
                    productoTienda.setCantidadEnStock(productoCantidad.getProducto().getCantidadEnStock());
                    productoTienda.setDisponibleParaVenta(productoCantidad.getProducto().getDisponibleParaVenta());
                }
            }
        }
        return tienda;
    }

    @Override
    public Tienda ponerNoDisponibleProducto(Tienda tienda, Producto producto) {
        tienda.getProductosEnStock().stream()
                .filter(p -> p.getId().equals(producto.getId()))
                .forEach(p -> p.setDisponibleParaVenta(false));
        return tienda;
    }

    private double calcularCostoTotal(List<ProductoCantidad> productosConCantidades) {
        return productosConCantidades.stream()
                .mapToDouble(pc -> pc.getProducto().getPrecioUnitario() * pc.getCantidad())
                .sum();
    }

    private int calcularCantidadTotal(List<ProductoCantidad> productosConCantidades) {
        return productosConCantidades.stream()
                .mapToInt(ProductoCantidad::getCantidad)
                .sum();
    }

    private List<String> generarDetalleOperacion(List<ProductoCantidad> productosConCantidades, double total, String tipoOperacion) {
        List<String> detalleOperacion = productosConCantidades.stream()
                .map(pc -> pc.getProducto().getId() + " " + pc.getProducto().getDescripcion() + " " + pc.getCantidad() + " x " + pc.getProducto().getPrecioUnitario())
                .collect(Collectors.toList());
        detalleOperacion.add("-----------------------------");
        detalleOperacion.add("TOTAL " + tipoOperacion + ": " + total);
        return detalleOperacion;
    }

    private boolean validarSaldo(Tienda tienda, double costoTotal, List<String> mensajes) {
        if (tienda.getSaldoEnCaja() < costoTotal) {
            mensajes.add("Los productos no podrán ser agregados a la tienda por saldo insuficiente en la caja.");
            return false;
        }
        return true;
    }

    private boolean validarStock(Tienda tienda, int totalCantidad, List<String> mensajes) {
        if (tienda.calcularStockTotal() + totalCantidad > tienda.getMaximoStock()) {
            mensajes.add("No se pueden agregar nuevos productos a la tienda ya que se alcanzó el máximo de stock.");
            return false;
        }
        return true;
    }

    private boolean validarCantidadProductos(int cantidad, List<String> mensajes) {
        if (cantidad > 3) {
            mensajes.add("La venta no puede incluir más de 3 productos diferentes. Se finaliza la venta.");
            return false;
        }
        return true;
    }

    private double procesarVentaProducto(Tienda tienda, ProductoCantidad productoCantidad, List<String> mensajes, List<String> detalleOperacion) {
        int cantidadSolicitada = productoCantidad.getCantidad();
        Producto producto = productoCantidad.getProducto();

        if (!producto.getDisponibleParaVenta()) {
            mensajes.add("El producto " + producto.getId() + " " + producto.getDescripcion() + " no se encuentra disponible.");
            return 0.0;
        }

        if (cantidadSolicitada > 12) {
            mensajes.add("La cantidad solicitada del producto " + producto.getId() + " " + producto.getDescripcion() + " excede el máximo permitido de 12 unidades. Se venderán solo 12 unidades.");
            cantidadSolicitada = 12;
        }

        int cantidadVendida = Math.min(cantidadSolicitada, producto.getCantidadEnStock());
        producto.setCantidadEnStock(producto.getCantidadEnStock() - cantidadVendida);
        double precioVenta = producto.getPrecioFinalVenta() * cantidadVendida;

        detalleOperacion.add(producto.getId() + " " + producto.getDescripcion() + " " + cantidadVendida + " x " + producto.getPrecioFinalVenta());

        if (cantidadVendida < cantidadSolicitada) {
            producto.setDisponibleParaVenta(false);
            mensajes.add("Hay productos con stock disponible menor al solicitado.");
        }

        return precioVenta;
    }
}
