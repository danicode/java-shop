package src.main.domain.tienda;

import src.main.domain.producto.Producto;
import src.main.domain.producto.tipo.ProductoComestible;
import src.main.domain.resultado.ResultadoValidacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {

    private String nombre;
    private Integer maximoStock;
    private Double saldoEnCaja;
    private List<Producto> productosDisponibleEnStock;

    public Tienda(String nombre, Integer maximoStock, Double saldoEnCaja) {
        this.nombre = nombre;
        this.maximoStock = maximoStock;
        this.saldoEnCaja = saldoEnCaja;
        this.productosDisponibleEnStock = new ArrayList<>();
    }

    public ResultadoValidacion<Tienda> validar() {
        ResultadoValidacion<Tienda> result = new ResultadoValidacion<>(this);
        if (nombre == null || nombre.isEmpty()) {
            result.getErrores().add("El nombre no puede estar vacío");
        }
        if (maximoStock == null) {
            result.getErrores().add("El valor máximo de stock no puede ser vacío");
        } else if (maximoStock <= 0) {
            result.getErrores().add("El valor máximo de stock debe ser mayor que cero");
        }
        if (saldoEnCaja == null) {
            result.getErrores().add("La cantidad no puede ser vacío");
        } else if (saldoEnCaja < 0) {
            result.getErrores().add("La cantidad no puede ser negativa");
        }
        return result;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getMaximoStock() {
        return maximoStock;
    }

    public Double getSaldoEnCaja() {
        return saldoEnCaja;
    }

    public void setSaldoEnCaja(Double saldoEnCaja) {
        this.saldoEnCaja = saldoEnCaja;
    }

    public List<Producto> getProductosDisponibleEnStock() {
        return productosDisponibleEnStock;
    }

    public void agregarProducto(Producto producto) {
        Producto productoExistente = this.productosDisponibleEnStock.stream()
                .filter(p -> p.equals(producto))
                .findFirst()
                .orElse(null);

        if (productoExistente == null) {
            this.productosDisponibleEnStock.add(producto);
        } else {
            productoExistente.setCantidadEnStock(
                    productoExistente.getCantidadEnStock() + producto.getCantidadEnStock()
            );
        }
    }

    public List<Producto> getProductosEnStock() {
        return productosDisponibleEnStock;
    }

    public Integer calcularStockTotal() {
        Integer stockTotal = 0;
        for (Producto producto : this.getProductosDisponibleEnStock()) {
            stockTotal += producto.getCantidadEnStock();
        }
        return stockTotal;
    }

    public String mostrarProductos(boolean soloDisponiblesParaVenta) {
        StringBuilder sb = new StringBuilder();
        for (Producto producto : this.getProductosDisponibleEnStock()) {
            if (!soloDisponiblesParaVenta || (producto.getCantidadEnStock() > 0 && producto.getDisponibleParaVenta())) {
                sb.append(producto).append("\n");
            }
        }

        if (sb.isEmpty()) {
            sb.append(soloDisponiblesParaVenta ? "No hay productos disponibles en stock" : "No hay productos");
        }

        return sb.toString();
    }

    public String obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        return productosDisponibleEnStock.stream()
                .filter(producto -> producto instanceof ProductoComestible) // Filtra los comestibles
                .map(producto -> (ProductoComestible) producto) // Convierte a ProductoComestible
                .filter(producto -> !producto.isImportado()) // Filtra los no importados
                .filter(producto -> producto.getPorcentajeDescuento() < porcentajeDescuento) // Filtra por descuento
                .sorted((p1, p2) -> Double.compare(p1.getPrecioFinalVenta(), p2.getPrecioFinalVenta())) // Ordena por precio de venta
                .map(producto -> producto.getDescripcion().toUpperCase()) // Convierte la descripción a mayúsculas
                .collect(Collectors.joining(", ")); // Junta los resultados en un string separado por comas
    }

    @Override
    public String toString() {
        return
                "- Nombre: '" + nombre + '\'' +
                "\n- Stock máximo: " + maximoStock +
                "\n- Saldo en Caja: " + saldoEnCaja;
    }
}
