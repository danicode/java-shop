package src.main.domain.producto;

import src.main.domain.resultado.ResultadoValidacion;

import java.util.Objects;

public abstract class Producto {

    protected String id;
    protected String descripcion;
    protected Double precioUnitario;
    protected Integer cantidadEnStock;
    protected Double porcentajeGanancia;
    protected Boolean disponibleParaVenta;
    protected Double porcentajeDescuento;

    public Producto(String id, String descripcion, Double precioUnitario, Integer cantidadEnStock, Double porcentajeGanancia, Boolean disponibleParaVenta, Double porcentajeDescuento) {
        this.setId(id);
        this.setDescripcion(descripcion);
        this.setPrecioUnitario(precioUnitario);
        this.setCantidadEnStock(cantidadEnStock);
        this.setPorcentajeGanancia(porcentajeGanancia);
        this.setDisponibleParaVenta(disponibleParaVenta);
        this.setPorcentajeDescuento(porcentajeDescuento);
    }

    protected abstract String getIdPrefijo();

    public ResultadoValidacion<? extends Producto> validar() {
        ResultadoValidacion<Producto> resultado = new ResultadoValidacion<>(this);
        if (validarId(getIdPrefijo())) {
            resultado.getErrores().add("Identificador no válido: debe comenzar con " + getIdPrefijo() + " seguido de 3 dígitos.");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            resultado.getErrores().add("La descripción no puede estar vacía");
        }
        if (precioUnitario == null) {
            resultado.getErrores().add("El precio por unidad no puede ser vacío");
        } else if (precioUnitario < 0) {
            resultado.getErrores().add("El precio por unidad no puede ser negativo");
        }
        if (cantidadEnStock == null) {
            resultado.getErrores().add("La cantidad en stock no puede ser vacío");
        } else if (cantidadEnStock < 0) {
            resultado.getErrores().add("La cantidad en stock no puede ser negativa");
        }
        if (porcentajeGanancia == null) {
            resultado.getErrores().add("El porcentaje de ganancia no puede ser vacío");
        } else if (porcentajeGanancia < 0 || porcentajeGanancia > 100) {
            resultado.getErrores().add("El porcentaje de ganancia debe estar entre 0 y 100");
        }
        if (porcentajeDescuento == null) {
            resultado.getErrores().add("El porcentaje de descuento no puede ser vacío");
        }
        return resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public Double getPrecioFinalVenta() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(Integer cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public Double getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(Double porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public Boolean getDisponibleParaVenta() {
        return disponibleParaVenta;
    }

    public void setDisponibleParaVenta(Boolean disponibleParaVenta) {
        this.disponibleParaVenta = disponibleParaVenta;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    protected boolean validarId(String prefijo) {
        return (id == null || !id.matches(prefijo + "\\d{3}"));
    }

    public Double calcularPrecioFinalConDescuento() {
        Double precioVentaInicial = this.precioUnitario * (1 + this.porcentajeGanancia / 100);
        Double descuento = precioVentaInicial * this.porcentajeDescuento / 100;

        return precioVentaInicial - descuento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return
                id + '\'' +
                " " + descripcion + '\'' +
                " - Precio Unitario: " + precioUnitario +
                " - Stock: " + cantidadEnStock +
                " - Porcentaje Ganancia: " + porcentajeGanancia +
                " - Disponible Para Venta: " + disponibleParaVenta +
                " - Porcentaje Descuento: " + porcentajeDescuento;
    }
}
