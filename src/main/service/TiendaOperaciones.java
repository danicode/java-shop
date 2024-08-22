package src.main.service;

import src.main.domain.producto.Producto;
import src.main.domain.producto.ProductoCantidad;
import src.main.domain.tienda.Tienda;
import src.main.service.resultado.ResultadoOperacion;

import java.util.List;

public interface TiendaOperaciones {

    ResultadoOperacion<Tienda> comprar(Tienda tienda, List<ProductoCantidad> productos);
    ResultadoOperacion<Tienda> vender(Tienda tienda, List<ProductoCantidad> productos);
    Tienda actualizarStock(Tienda tienda, List<ProductoCantidad> productosActualizados);
    Tienda ponerNoDisponibleProducto(Tienda tienda, Producto producto);
}
