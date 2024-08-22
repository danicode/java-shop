package src.main.domain.producto.tipo;

import src.main.domain.resultado.ResultadoValidacion;

import java.time.LocalDate;

public class Bebida extends ProductoComestible implements CalculoCalorias {

    public static final String ID_PREFIJO = "AC";

    private Double graduacionAlcoholica;

    public Bebida(String id, String descripcion, Double precioUnitario, Integer cantidadEnStock, Double porcentajeGanancia, Boolean disponibleParaVenta, LocalDate fechaVencimiento, Double calorias, double graduacionAlcoholica, Boolean importado, Double porcentajeDescuento) {
        super(id, descripcion, precioUnitario, cantidadEnStock, porcentajeGanancia, disponibleParaVenta, fechaVencimiento, calorias, importado, porcentajeDescuento);
        this.setGraduacionAlcoholica(graduacionAlcoholica);
        this.setCalorias(this.calcularCalorias());
    }

    @Override
    public ResultadoValidacion<Bebida> validar() {
        ResultadoValidacion<Bebida> resultado = new ResultadoValidacion<>(this);

        // Validación común en Producto
        ResultadoValidacion<? extends ProductoComestible> resultadoBase = super.validar();
        if (!resultadoBase.isValido()) {
            resultado.getErrores().addAll(resultadoBase.getErrores());
        }
        if (porcentajeDescuento != null && porcentajeDescuento > 10) {
            resultado.getErrores().add("El porcentaje de descuento para bebidas no puede superar el 10%");
        }

        return resultado;
    }

    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public void setGraduacionAlcoholica(double graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    @Override
    protected String getIdPrefijo() {
        return ID_PREFIJO;
    }

    @Override
    public Double calcularCalorias() {
        if (this.graduacionAlcoholica != null) {
            if (this.graduacionAlcoholica >= 0 && this.graduacionAlcoholica <= 2) {
                return this.calorias; // Mantener las calorías ingresadas
            } else if (this.graduacionAlcoholica > 2 && this.graduacionAlcoholica <= 4.5) {
                return this.calorias * 1.25;
            } else if (this.graduacionAlcoholica > 4.5) {
                return this.calorias * 1.5;
            }
        }
        return this.calorias; // Valor por defecto
    }

    @Override
    public String toString() {
        return id + '\'' +
                " - Descripción: '" + descripcion + '\'' +
                " - Graduación Alcohólica: " + graduacionAlcoholica +
                " - Fecha Vto: " + fechaVencimiento +
                " - Calorías: " + calorias +
                " - Importado: " + (importado ? "Si" : "No") +
                " - Precio Unitario: " + precioUnitario +
                " - Stock: " + cantidadEnStock +
                " - Porcentaje Ganancia: " + porcentajeGanancia +
                " - Disponible Para Venta: " + (disponibleParaVenta ? "Si" : "No") +
                " - Porcentaje Descuento: " + porcentajeDescuento;
    }
}
