
package Clases;
//Datos en base: 
//idDetalle AUTO_INCREMENT, idVenta AUTO_INCREMENT, idProductos, cantidad
//precio, subtotal

public class DetalleCompra extends Detalles {
    
     public DetalleCompra(int cantidad, float precio) {
        super(cantidad, precio);
    }
    
}
