package src.main.domain.producto.tipo;

import src.main.domain.producto.Producto;
import src.main.domain.resultado.ResultadoValidacion;

public class Limpieza extends Producto {

    public static final String ID_PREFIJO = "AZ";
    public enum TipoAplicacion { COCINA, BANIO, ROPA, MULTIUSO }

    private TipoAplicacion tipoAplicacion;

    public Limpieza(String id, String descripcion, Double precioUnitario, Integer cantidadEnStock, Double porcentajeGanancia, Boolean disponibleParaVenta, TipoAplicacion tipoAplicacion, Double porcentajeDescuento) {
        super(id, descripcion, precioUnitario, cantidadEnStock, porcentajeGanancia, disponibleParaVenta, porcentajeDescuento);
        this.setTipoAplicacion(tipoAplicacion);
    }

    @Override
    public ResultadoValidacion<Limpieza> validar() {
        ResultadoValidacion<Limpieza> resultado = new ResultadoValidacion<>(this);

        // Validación común en Producto
        ResultadoValidacion<? extends Producto> resultadoBase = super.validar();
        if (!resultadoBase.isValido()) {
            resultado.getErrores().addAll(resultadoBase.getErrores());
        }
        if (this.tipoAplicacion == null) {
            resultado.getErrores().add("El tipo de aplicación no puede ser vacío.");
        } else if (this.tipoAplicacion != TipoAplicacion.COCINA && this.tipoAplicacion != TipoAplicacion.MULTIUSO) {
            if (porcentajeGanancia < 10 || porcentajeGanancia > 25) {
                resultado.getErrores().add("El porcentaje de ganancia para productos de limpieza debe estar entre 10% y 25%");
            }
        }
        if (porcentajeDescuento != null && porcentajeDescuento > 20) {
            resultado.getErrores().add("El porcentaje de descuento para productos de limpieza no puede superar el 20%");
        }
        return resultado;
    }

    public TipoAplicacion getTipoAplicacion() {
        return tipoAplicacion;
    }

    public void setTipoAplicacion(TipoAplicacion tipoAplicacion) {
        this.tipoAplicacion = tipoAplicacion;
    }

    @Override
    protected String getIdPrefijo() {
        return ID_PREFIJO;
    }

    @Override
    public String toString() {
        return id + '\'' +
                " - Descripción: '" + descripcion + '\'' +
                " - Tipo Aplicación: " + tipoAplicacion +
                " - Precio Unitario: " + precioUnitario +
                " - Stock: " + cantidadEnStock +
                " - Porcentaje Ganancia: " + porcentajeGanancia +
                " - Disponible Para Venta: " + (disponibleParaVenta ? "Si" : "No") +
                " - Porcentaje Descuento: " + porcentajeDescuento;
    }
}
