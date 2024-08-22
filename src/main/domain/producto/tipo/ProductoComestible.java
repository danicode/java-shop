package src.main.domain.producto.tipo;

import src.main.domain.producto.Producto;
import src.main.domain.resultado.ResultadoValidacion;

import java.time.LocalDate;

public abstract class ProductoComestible extends Producto implements IImportados {
    protected LocalDate fechaVencimiento;
    protected Double calorias;
    protected Boolean importado;

    public ProductoComestible(String id, String descripcion, Double precioUnitario, Integer cantidadEnStock, Double porcentajeGanancia, Boolean disponibleParaVenta, LocalDate fechaVencimiento, Double calorias, Boolean importado, Double porcentajeDescuento) {
        super(id, descripcion, precioUnitario, cantidadEnStock, porcentajeGanancia, disponibleParaVenta, porcentajeDescuento);
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calorias;
        this.importado = importado;
    }

    @Override
    public ResultadoValidacion<? extends ProductoComestible> validar() {
        ResultadoValidacion<ProductoComestible> resultado = new ResultadoValidacion<>(this);

        // Validación común en Producto
        ResultadoValidacion<? extends Producto> resultadoBase = super.validar();
        if (!resultadoBase.isValido()) {
            resultado.getErrores().addAll(resultadoBase.getErrores());
        }
        if (fechaVencimiento == null) {
            resultado.getErrores().add("La fecha de vencimiento no puedo ser vacía");
        }
        if (calorias == null) {
            resultado.getErrores().add("Las calorías no puede ser vacía");
        } else if (calorias < 0) {
            resultado.getErrores().add("Las calorías no pueden ser negativas");
        }
        return resultado;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    @Override
    public Boolean isImportado() {
        return this.importado;
    }

    @Override
    public void setImportado(Boolean importado) {
        this.importado = importado;
    }

    @Override
    public Double getPrecioFinalVenta() {
        double precioFinal = this.getPrecioUnitario();
        if (this.importado) {
            precioFinal *= 1.12; // Aplicar un 12% de impuesto
        }
        return precioFinal;
    }

    @Override
    public String toString() {
        return "ProductoComestible{" +
                "fechaVencimiento=" + fechaVencimiento +
                ", calorias=" + calorias +
                ", importado=" + importado +
                ", id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", cantidadEnStock=" + cantidadEnStock +
                ", porcentajeGanancia=" + porcentajeGanancia +
                ", disponibleParaVenta=" + disponibleParaVenta +
                ", porcentajeDescuento=" + porcentajeDescuento +
                '}';
    }
}
