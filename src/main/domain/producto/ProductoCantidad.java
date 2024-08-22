package src.main.domain.producto;

public class ProductoCantidad {

    private Producto producto;
    private int cantidad;

    public ProductoCantidad(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}
