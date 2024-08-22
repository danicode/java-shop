package src.main.domain.producto.tipo;

import src.main.domain.resultado.ResultadoValidacion;

import java.time.LocalDate;

public class Envasado extends ProductoComestible  {

    public static final String ID_PREFIJO = "AB";

    public enum TipoEnvase {
        BOTELLA, LATA, TETRA_PACK, CAJA
    }

    private TipoEnvase tipoEnvase;

    public Envasado(String id, String descripcion, Double precioUnitario, Integer cantidadEnStock, Double porcentajeGanancia, Boolean disponibleParaVenta, LocalDate fechaVencimiento, Double calorias, TipoEnvase tipoEnvase, Boolean importado, Double porcentajeDescuento) {
        super(id, descripcion, precioUnitario, cantidadEnStock, porcentajeGanancia, disponibleParaVenta, fechaVencimiento, calorias, importado, porcentajeDescuento);
        this.tipoEnvase = tipoEnvase;
    }

    @Override
    public ResultadoValidacion<Envasado> validar() {
        ResultadoValidacion<Envasado> resultado = new ResultadoValidacion<>(this);

        // Validación común en Producto
        ResultadoValidacion<? extends ProductoComestible> resultadoBase = super.validar();
        if (!resultadoBase.isValido()) {
            resultado.getErrores().addAll(resultadoBase.getErrores());
        }
        if (porcentajeDescuento != null && porcentajeDescuento > 15) {
            resultado.getErrores().add("El porcentaje de descuento para productos envasados no puede superar el 15%");
        }

        return resultado;
    }

    @Override
    protected String getIdPrefijo() {
        return ID_PREFIJO;
    }

    @Override
    public String toString() {
        return id + '\'' +
                " - Descripción: '" + descripcion + '\'' +
                " - Tipo Envase: " + tipoEnvase +
                " - Fecha Vto: " + fechaVencimiento +
                " - Calorías: " + calorias +
                " - Importado: " + (importado ? "Si" : "No") +
                " - Precio Unitario: " + precioUnitario +
                " - Stock:" + cantidadEnStock +
                " - Porcentaje Ganancia: " + porcentajeGanancia +
                " - Disponible Para Venta: " + (disponibleParaVenta ? "Si" : "No") +
                " - Porcentaje Descuento: " + porcentajeDescuento;
    }
}
