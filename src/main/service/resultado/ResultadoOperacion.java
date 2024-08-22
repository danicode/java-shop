package src.main.service.resultado;

import java.util.List;

public class ResultadoOperacion<T> {
    private boolean exito;
    private double total;
    private List<String> mensajes;
    private List<String> detalleOperacion;
    private T objeto;

    public ResultadoOperacion(T objeto, boolean exito, double total, List<String> mensajes, List<String> detalleOperacion) {
        this.objeto = objeto;
        this.exito = exito;
        this.total = total;
        this.mensajes = mensajes;
        this.detalleOperacion = detalleOperacion;
    }

    public T getObjeto() { return objeto; }

    public boolean isExito() {
        return exito;
    }

    public double getTotal() {
        return this.total;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public List<String> getDetalleOperacion() {
        return detalleOperacion;
    }
}
